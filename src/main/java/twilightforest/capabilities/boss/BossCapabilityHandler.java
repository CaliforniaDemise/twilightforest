package twilightforest.capabilities.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;
import twilightforest.enums.BossVariant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BossCapabilityHandler implements IBossCapability, INBTSerializable<NBTTagCompound> {

    private BossVariant variant;
    private BlockPos homePos;

    public BossCapabilityHandler() {
        this.variant = null;
        this.homePos = BlockPos.ORIGIN;
    }

    @Nullable
    @Override
    public BossVariant getBossVariant() {
        return this.variant;
    }

    @Override
    @Nonnull
    public BlockPos getHomePos() {
        return this.homePos;
    }

    @Nonnull
    public final BlockPos getHomePos(Entity entity) {
        BlockPos position = entity instanceof EntityCreature && this.homePos == BlockPos.ORIGIN ? ((EntityCreature) entity).getHomePosition() : this.homePos;
        if (position == null) position = BlockPos.ORIGIN;
        return position;
    }

    @Override
    public void setBossVariant(BossVariant variant) {
        this.variant = variant;
    }

    @Override
    public void setHomePos(@Nonnull BlockPos homePos) {
        this.homePos = homePos;
    }

    public void initBoss(BossVariant variant, BlockPos homePos) {
        this.variant = variant;
        this.homePos = homePos;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        if (this.variant != null) tag.setInteger("variant", this.variant.ordinal());
        tag.setTag("homePos", NBTUtil.createPosTag(this.homePos));
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        this.initBoss(BossVariant.getVariant(tag.getInteger("variant")), NBTUtil.getPosFromTag(tag.getCompoundTag("homePos")));
    }
}
