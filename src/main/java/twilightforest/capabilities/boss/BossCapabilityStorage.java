package twilightforest.capabilities.boss;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class BossCapabilityStorage implements Capability.IStorage<IBossCapability> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IBossCapability> capability, IBossCapability instance, EnumFacing side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IBossCapability> capability, IBossCapability instance, EnumFacing side, NBTBase nbt) {
        assert nbt instanceof NBTTagCompound;
        instance.deserializeNBT((NBTTagCompound) nbt);
    }
}
