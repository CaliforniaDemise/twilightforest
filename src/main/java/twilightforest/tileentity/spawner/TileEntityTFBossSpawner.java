package twilightforest.tileentity.spawner;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.boss.IBossCapability;
import twilightforest.enums.BossVariant;
import twilightforest.events.BossEvent;

public abstract class TileEntityTFBossSpawner extends TileEntity implements ITickable {

	protected static final int SHORT_RANGE = 9, LONG_RANGE = 50;

	private final BossVariant variant;
	protected final ResourceLocation mobID;
	protected Entity displayCreature = null;
	protected boolean spawnedBoss = false;
	private EntityLivingBase living = null;

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
		if (playerCheck && this.world.isRemote) {
			// particles
			double rx = pos.getX() + world.rand.nextFloat();
			double ry = pos.getY() + world.rand.nextFloat();
			double rz = pos.getZ() + world.rand.nextFloat();
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.FLAME, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			return;
		}
		if (this.living == null) this.living = this.makeMyCreature();
		BossEvent.Spawning event = new BossEvent.Spawning(this.world, this.pos, this.world.getBlockState(this.pos), this, this.living);
		MinecraftForge.EVENT_BUS.post(event);
		Event.Result result = event.getResult();
		if (result == Event.Result.DENY) return;
		boolean check = result == Event.Result.ALLOW || playerCheck;
		if (check) {
			if (world.getDifficulty() == EnumDifficulty.PEACEFUL && this.living instanceof EntityMob) return;
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

	/**
	 * Get a temporary copy of the creature we're going to summon for display purposes
	 */
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

	protected EntityLivingBase makeMyCreature() {
		EntityLivingBase living = (EntityLivingBase) EntityList.createEntityByIDFromName(mobID, world);
		BossEvent.Construction event = new BossEvent.Construction(this.world, this.pos, this.world.getBlockState(this.pos), this, living);
		MinecraftForge.EVENT_BUS.post(event);
		living = event.getModifiedBoss();
		assert living == null;
		IBossCapability capability = living.getCapability(CapabilityList.BOSS, null);
		if (capability == null) return living;
		capability.setBossVariant(this.variant);
		capability.setHomePos(this.pos);
		return living;
	}
}
