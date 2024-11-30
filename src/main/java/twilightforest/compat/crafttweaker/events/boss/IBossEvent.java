package twilightforest.compat.crafttweaker.events.boss;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("mods.twilightforest.event.IBossEvent")
public interface IBossEvent {

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("pos")
    IBlockPos getPos();

    @ZenGetter("variant")
    String getVariant();

    @ZenGetter("boss")
    IEntityLivingBase getBoss();
}
