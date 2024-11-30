package twilightforest.compat.crafttweaker.events.boss;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.IEventHasResult;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import twilightforest.events.BossEvent;

import java.util.Locale;

@ZenRegister
@ZenClass("mods.twilightforest.event.BossSpawnEvent")
public class BossSpawnEvent implements IBossEvent, IEventHasResult {

    private final IWorld world;
    private final IBlockPos pos;
    private final String variant;
    private final IEntityLivingBase boss;
    private String result;

    public BossSpawnEvent(BossEvent.Spawning event) {
        this.world = CraftTweakerMC.getIWorld(event.getWorld());
        this.pos = CraftTweakerMC.getIBlockPos(event.getPos());
        this.variant = event.getVariant().getName();
        this.boss = CraftTweakerMC.getIEntityLivingBase(event.getBoss());
        this.result = event.getResult().name().toLowerCase(Locale.US);
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
    public String getResult() {
        return this.result;
    }

    @Override
    public void setDenied() {
        this.result = "deny";
    }

    @Override
    public void setDefault() {
        this.result = "default";
    }

    @Override
    public void setAllowed() {
        this.result = "allow";
    }
}
