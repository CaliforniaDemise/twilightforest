package twilightforest.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import twilightforest.capabilities.boss.BossCapabilityHandler;
import twilightforest.capabilities.boss.BossCapabilityStorage;
import twilightforest.capabilities.boss.IBossCapability;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.capabilities.shield.ShieldCapabilityHandler;
import twilightforest.capabilities.shield.ShieldCapabilityStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityList {

	@CapabilityInject(IShieldCapability.class)
	public static final Capability<IShieldCapability> SHIELDS;

	@CapabilityInject(IBossCapability.class)
	public static final Capability<IBossCapability> BOSS;

	static {
		SHIELDS = null;
		BOSS = null;
	}

	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IBossCapability.class, new BossCapabilityStorage(), BossCapabilityHandler::new);
		CapabilityManager.INSTANCE.register(IShieldCapability.class, new ShieldCapabilityStorage(), ShieldCapabilityHandler::new);
		MinecraftForge.EVENT_BUS.register(CapabilityList.class);
	}

	@SubscribeEvent
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityLivingBase) {
			e.addCapability(IShieldCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				final IShieldCapability inst = SHIELDS.getDefaultInstance();

				{
					inst.setEntity((EntityLivingBase) e.getObject());
				}

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == SHIELDS;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == SHIELDS ? SHIELDS.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) SHIELDS.getStorage().writeNBT(SHIELDS, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					SHIELDS.getStorage().readNBT(SHIELDS, inst, null, nbt);
				}

			});
			e.addCapability(IBossCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				final IBossCapability inst = BOSS.getDefaultInstance();

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) BOSS.getStorage().writeNBT(BOSS, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					 BOSS.getStorage().readNBT(BOSS, inst, null, nbt);
				}

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
					return capability == BOSS;
				}

				@Nullable
				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
					return capability == BOSS ? BOSS.<T>cast(inst) : null;
				}
			});
		}
	}
}
