package twilightforest.entity.boss;

import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import twilightforest.TFConfig;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.entity.MultiplayerFlexibleEnemy;
import twilightforest.loot.TFLootTables;
import twilightforest.util.EntityUtil;
import twilightforest.util.LandmarkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseTFBoss extends Monster implements MultiplayerFlexibleEnemy, EnforcedHomePoint {
	private static final EntityDataAccessor<Optional<GlobalPos>> HOME_POINT = SynchedEntityData.defineId(BaseTFBoss.class, EntityDataSerializers.OPTIONAL_GLOBAL_POS);
	private final List<ServerPlayer> hurtBy = new ArrayList<>();
	private final NonNullList<ItemStack> dyingInventory = NonNullList.withSize(27, ItemStack.EMPTY);
	private static final UUID GROUP_HEALTH_UUID = UUID.fromString("7fe91103-8bbf-4010-9c0a-67cd866b5185");

	protected BaseTFBoss(EntityType<? extends Monster> type, Level level) {
		super(type, level);
	}

	public abstract ResourceKey<Structure> getHomeStructure();

	public abstract ServerBossEvent getBossBar();

	public abstract Block getDeathContainer(RandomSource random);

	public abstract Block getBossSpawner();

	protected boolean shouldSpawnLoot() {
		return true;
	}

	protected boolean shouldCreateSpawner() {
		return true;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(HOME_POINT, Optional.empty());
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		if (!this.level().isClientSide()) {
			this.getBossBar().setProgress(this.getHealth() / this.getMaxHealth());
		}
	}

	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.getBossBar().setName(this.getDisplayName());
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.getBossBar().addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.getBossBar().removePlayer(player);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		this.saveHomePointToNbt(compound);
		this.addDeathItemsSaveData(compound);
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.readDeathItemsSaveData(compound);
		this.loadHomePointFromNbt(compound);
		if (this.hasCustomName()) {
			this.getBossBar().setName(this.getDisplayName());
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean actuallyHurt = super.hurt(source, amount);
		if (actuallyHurt && source.getEntity() != null) {
			this.maybeAddQualifiedPlayer(source.getEntity());
		}
		return actuallyHurt;
	}

	@Override
	public void lavaHurt() {
		if (!this.fireImmune()) {
			this.setSecondsOnFire(5);
			if (this.hurt(this.damageSources().lava(), 4.0F)) {
				this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.getRandom().nextFloat() * 0.4F);
				EntityUtil.killLavaAround(this);
			}
		}
	}

	@Override
	public void die(DamageSource cause) {
		super.die(cause);
		if (this.shouldSpawnLoot()) {
			// mark the boss structure as conquered
			if (this.level() instanceof ServerLevel server) {
				this.getBossBar().setProgress(0.0F);
				IBossLootBuffer.saveDropsIntoBoss(this, TFLootTables.createLootParams(this, true, cause).create(LootContextParamSets.ENTITY), server);
				LandmarkUtil.markStructureConquered(this.level(), this, this.getHomeStructure(), true);
				this.grantGroupAdvancement(this);
			}
		}
	}

	@Override
	public void remove(RemovalReason reason) {
		if (reason.equals(RemovalReason.KILLED) && this.shouldSpawnLoot() && this.level() instanceof ServerLevel serverLevel) {
			IBossLootBuffer.depositDropsIntoChest(this, this.getDeathContainer(this.getRandom()).defaultBlockState().setValue(ChestBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(this.level().getRandom())), EntityUtil.bossChestLocation(this), serverLevel);
		}
		super.remove(reason);
	}

	@Override
	public void checkDespawn() {
		if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
			if (this.shouldCreateSpawner() && this.isRestrictionPointValid(this.level().dimension()) && this.level().isLoaded(this.getRestrictionPoint().pos())) {
				this.level().setBlockAndUpdate(this.getRestrictionPoint().pos(), this.getBossSpawner().defaultBlockState());
			}
			this.discard();
		} else {
			super.checkDespawn();
		}
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	protected boolean shouldDropLoot() {
		return !TFConfig.COMMON_CONFIG.bossDropChests.get();
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return false;
	}

	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}

	@Override
	public boolean isPushedByFluid(FluidType type) {
		return false;
	}

	@Override
	protected float getWaterSlowDown() {
		return 1.0F;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public @Nullable GlobalPos getRestrictionPoint() {
		return this.getEntityData().get(HOME_POINT).orElse(null);
	}

	@Override
	public void setRestrictionPoint(@Nullable GlobalPos pos) {
		this.getEntityData().set(HOME_POINT, Optional.ofNullable(pos));
	}

	@Override
	public NonNullList<ItemStack> getItemStacks() {
		return this.dyingInventory;
	}

	@Override
	public List<ServerPlayer> getQualifiedPlayers() {
		return this.hurtBy;
	}
}
