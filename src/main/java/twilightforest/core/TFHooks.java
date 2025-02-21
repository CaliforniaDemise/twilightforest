package twilightforest.core;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.math.BlockPos;
import twilightforest.block.BlockTFBossSpawner;
import twilightforest.block.TFBlocks;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.boss.IBossCapability;
import twilightforest.entity.boss.EntityTFHydra;
import twilightforest.entity.boss.EntityTFKnightPhantom;
import twilightforest.entity.boss.HydraHeadContainer;

public class TFHooks {
    @SuppressWarnings("unused")
    public static void handleBossDespawning(EntityLiving entity) {
        if (!entity.isDead) return;
        IBossCapability capability = entity.getCapability(CapabilityList.BOSS, null);
        if (capability == null || !capability.isBoss()) {
            if (capability != null && entity instanceof EntityMagmaCube) System.out.println(capability.getBossVariant());
            return;
        }
        BlockPos pos = capability.getHomePos(entity);
        if (pos == BlockPos.ORIGIN) return;
        if (entity instanceof EntityTFKnightPhantom && ((EntityTFKnightPhantom) entity).getNumber() != 0) return;
        if (entity instanceof EntityTFHydra) {
            for (HydraHeadContainer container : ((EntityTFHydra) entity).hc) {
                if (container.headEntity != null) container.headEntity.setDead();
            }
        }
        entity.getEntityWorld().setBlockState(pos, TFBlocks.boss_spawner.getDefaultState().withProperty(BlockTFBossSpawner.VARIANT, capability.getBossVariant()));
    }
}
