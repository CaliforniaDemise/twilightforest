package twilightforest.tileentity.spawner;

import net.minecraft.entity.EntityList;
import twilightforest.entity.boss.EntityTFNaga;
import twilightforest.enums.BossVariant;

public class TileEntityTFNagaSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFNagaSpawner() {
		super(EntityList.getKey(EntityTFNaga.class), BossVariant.NAGA);
	}

	@Override
	public int getRange() {
		return LONG_RANGE;
	}
}
