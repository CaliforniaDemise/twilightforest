package twilightforest.tileentity.spawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import twilightforest.entity.boss.EntityTFHydra;
import twilightforest.entity.boss.EntityTFHydraHead;
import twilightforest.enums.BossVariant;

public class TileEntityTFHydraSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFHydraSpawner() {
		super(EntityList.getKey(EntityTFHydra.class), BossVariant.HYDRA);
	}

	@Override
	public int getRange() {
		return LONG_RANGE;
	}

	@Override
	public Entity getDisplayEntity() {
		if (this.displayCreature == null) {
			this.displayCreature = EntityList.createEntityByIDFromName(EntityList.getKey(EntityTFHydraHead.class), world);
		}
		return this.displayCreature;
	}
}
