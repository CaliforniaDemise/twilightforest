package twilightforest.tileentity.spawner;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import twilightforest.entity.EntityTFKobold;
import twilightforest.entity.boss.EntityTFYetiAlpha;
import twilightforest.enums.BossVariant;
import twilightforest.events.BossEvent;

public class TileEntityTFFinalBossSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFFinalBossSpawner() {
		super(EntityList.getKey(EntityTFKobold.class), BossVariant.FINAL_BOSS);
	}

	@Override
	protected EntityLivingBase makeMyCreature() {
		EntityLivingBase living = (EntityLivingBase) EntityList.createEntityByIDFromName(mobID, world);
		assert living != null;
		living.setCustomNameTag("Final Boss");
		living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024);
		living.setHealth(living.getMaxHealth());
		BossEvent.Construction event = new BossEvent.Construction(this.world, this.pos, this.world.getBlockState(this.pos), this, living);
		MinecraftForge.EVENT_BUS.post(event);
		living = event.getModifiedBoss();
		return living;
	}

	@Override
	protected void initializeCreature(EntityLivingBase myCreature) {
		super.initializeCreature(myCreature);

	}
}
