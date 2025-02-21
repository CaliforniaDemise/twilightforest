package twilightforest.tileentity.spawner;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.boss.IBossCapability;
import twilightforest.client.renderer.tileentity.TileEntityTFBossSpawnerRenderer;
import twilightforest.enums.BossVariant;
import twilightforest.events.BossEvent;

import javax.annotation.Nullable;

public abstract class TileEntityTFBossSpawner extends TileEntity implements ITickable {

	protected static final int SHORT_RANGE = 9, LONG_RANGE = 50;

	private final BossVariant variant;
	protected final ResourceLocation mobID;
	protected Entity displayCreature = null;
	protected boolean spawnedBoss = false;
	private EntityLivingBase living = null;
	private int renderTick = 0;

	protected TileEntityTFBossSpawner(ResourceLocation mobID, BossVariant variant) {
		this.mobID = mobID;
		this.variant = variant;
	}

	public boolean anyPlayerInRange() {
		return world.isAnyPlayerWithinRangeAt(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange());
	}

	public BossVariant getVariant() {
		return variant;
	}

	@Override
	public void update() {
		if (this.spawnedBoss) return;
		boolean playerCheck = this.anyPlayerInRange();
		if (this.world.isRemote) {
			if (this.renderTick == 1800) this.renderTick = 0;
			++this.renderTick;
			if (playerCheck) {
				// particles
				double rx = pos.getX() + world.rand.nextFloat();
				double ry = pos.getY() + world.rand.nextFloat();
				double rz = pos.getZ() + world.rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, rx, ry, rz, 0.0D, 0.0D, 0.0D);
				world.spawnParticle(EnumParticleTypes.FLAME, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			}
			return;
		}
		if (this.living == null) this.living = this.makeMyCreature();
		if (this.living == null) return;
		BossEvent.Spawning event = new BossEvent.Spawning(this.world, this.pos, this.world.getBlockState(this.pos), this, this.living);
		MinecraftForge.EVENT_BUS.post(event);
		Event.Result result = event.getResult();
		if (result == Event.Result.DENY) return;
		boolean check = result == Event.Result.ALLOW || playerCheck;
		if (check) {
			if (world.getDifficulty() == EnumDifficulty.PEACEFUL && this.living instanceof IMob) return;
			if (!this.world.isRemote && this.spawnMyBoss(this.living)) {
				this.world.destroyBlock(this.pos, false);
				this.spawnedBoss = true;
				this.living = null;
			}
		}
	}

	/**
	 * Spawn the boss
	 */
	protected boolean spawnMyBoss(EntityLivingBase myCreature) {
		myCreature.moveToBlockPosAndAngles(pos, world.rand.nextFloat() * 360F, 0.0F);
		if (myCreature instanceof EntityLiving) ((EntityLiving) myCreature).onInitialSpawn(world.getDifficultyForLocation(pos), null);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		return world.spawnEntity(myCreature);
	}

	@SideOnly(Side.CLIENT)
	public int getRenderTick() {
		return renderTick;
	}

	/**
	 * Get a temporary copy of the creature we're going to summon for display purposes.
	 * Used in {@link TileEntityTFBossSpawnerRenderer#renderMob(TileEntityTFBossSpawner, double, double, double, float)}
	 */
	@SideOnly(Side.CLIENT)
	public Entity getDisplayEntity() {
		if (this.displayCreature == null) {
			this.displayCreature = makeMyCreature();
		}
		return this.displayCreature;
	}

	/**
	 * Any post-creation initialization goes here
	 */
	protected void initializeCreature(EntityLivingBase myCreature) {
		if (myCreature instanceof EntityCreature) {
			((EntityCreature) myCreature).setHomePosAndDistance(pos, 46);
		}
	}

	public int getRange() {
		return SHORT_RANGE;
	}

	@Nullable
	protected EntityLivingBase makeMyCreature() {
		EntityLivingBase living = (EntityLivingBase) EntityList.createEntityByIDFromName(mobID, world);
		BossEvent.Construction event = new BossEvent.Construction(this.world, this.pos, this.world.getBlockState(this.pos), this, living);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getModifiedBoss() != null) living = event.getModifiedBoss();
		else return living;
		if (living == null) return null;
		if (!this.world.isRemote) {
			IBossCapability capability = living.getCapability(CapabilityList.BOSS, null);
			if (capability == null) return living;
			capability.setBossVariant(this.variant);
			capability.setHomePos(this.pos);
		}
		return living;
	}
}
