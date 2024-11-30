package twilightforest.capabilities.shield;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class ShieldCapabilityStorage implements Capability.IStorage<IShieldCapability> {

	@Override
	public NBTTagCompound writeNBT(Capability<IShieldCapability> capability, IShieldCapability instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<IShieldCapability> capability, IShieldCapability instance, EnumFacing side, NBTBase nbt) {
		assert nbt instanceof NBTTagCompound;
		instance.deserializeNBT((NBTTagCompound) nbt);
	}
}
