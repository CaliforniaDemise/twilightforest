package twilightforest.compat.crafttweaker.events.boss;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import twilightforest.events.BossEvent;

@ZenRegister
@ZenClass("mods.twilightforest.event.BossDeathEvent")
public class BossDeathEvent implements IBossEvent, IEventCancelable {

    private final IWorld world;
    private final IBlockPos pos;
    private final String variant;
    private final IEntityLivingBase boss;
    private boolean canceled = false;

    public BossDeathEvent(BossEvent.Death event) {
        this.world = CraftTweakerMC.getIWorld(event.getWorld());
        this.pos = CraftTweakerMC.getIBlockPos(event.getPos());
        this.variant = event.getVariant().getName();
        this.boss = CraftTweakerMC.getIEntityLivingBase(event.getBoss());
    }

    @Override
    public IWorld getWorld() {
        return this.world;
    }

    @Override
    public IBlockPos getPos() {
        return this.pos;
    }

    @Override
    public String getVariant() {
        return this.variant;
    }

    @Override
    public IEntityLivingBase getBoss() {
        return this.boss;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public void setCanceled(boolean b) {
        this.canceled = b;
    }
}
