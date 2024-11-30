package twilightforest.capabilities.boss;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;
import twilightforest.TwilightForestMod;
import twilightforest.capabilities.CapabilityList;
import twilightforest.enums.BossVariant;

public interface IBossCapability extends INBTSerializable<NBTTagCompound> {

    ResourceLocation ID = TwilightForestMod.prefix("cap_boss");

    BossVariant getBossVariant();

    BlockPos getHomePos();

    default boolean isBoss() {
        return this.getBossVariant() != null;
    }

    void setBossVariant(BossVariant variant);

    void setHomePos(BlockPos pos);

    static void initBoss(EntityLivingBase entity, BossVariant variant) {
        IBossCapability boss =  entity.getCapability(CapabilityList.BOSS, null);
        if (boss == null || boss.getBossVariant() != null) return;
        boss.setBossVariant(variant);
        boss.setHomePos(new BlockPos(entity));
    }
}
