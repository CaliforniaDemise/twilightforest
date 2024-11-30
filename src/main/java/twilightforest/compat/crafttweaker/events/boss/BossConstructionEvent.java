package twilightforest.compat.crafttweaker.events.boss;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import twilightforest.events.BossEvent;

@ZenRegister
@ZenClass("mods.twilightforest.event.BossConstructionEvent")
public class BossConstructionEvent implements IBossEvent {

    private final IWorld world;
    private final IBlockPos pos;
    private final String variant;
    private final IEntityLivingBase boss;
    public IEntityLivingBase modifiedBoss;

    public BossConstructionEvent(BossEvent.Construction event) {
        this.world = CraftTweakerMC.getIWorld(event.getWorld());
        this.pos = CraftTweakerMC.getIBlockPos(event.getPos());
        this.variant = event.getVariant().getName();
        this.boss = CraftTweakerMC.getIEntityLivingBase(event.getBoss());
        this.modifiedBoss = CraftTweakerMC.getIEntityLivingBase(event.getModifiedBoss());
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

    @ZenMethod
    public void setBoss(IEntity modifiedBoss) {
        if (modifiedBoss instanceof IEntityLivingBase) {
            this.modifiedBoss = (IEntityLivingBase) modifiedBoss;
        }
        else CraftTweakerAPI.logError("Could not cast " + modifiedBoss + " to IEntityLivingBase");
    }
}
