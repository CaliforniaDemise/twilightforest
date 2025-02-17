package twilightforest.capabilities.shield;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import twilightforest.TwilightForestMod;

public interface IShieldCapability extends INBTSerializable<NBTTagCompound> {

	ResourceLocation ID = TwilightForestMod.prefix("cap_shield");

	void setEntity(EntityLivingBase entity);

	void update();

	int shieldsLeft();

	int temporaryShieldsLeft();

	int permanentShieldsLeft();

	void breakShield();

	void replenishShields();

	void setShields(int amount, boolean temp);

    void addShields(int amount, boolean temp);
}
