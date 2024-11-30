package twilightforest.compat.crafttweaker.events;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import twilightforest.compat.crafttweaker.events.boss.BossConstructionEvent;
import twilightforest.compat.crafttweaker.events.boss.BossSpawnEvent;
import twilightforest.events.BossEvent;

import java.util.Locale;

@ZenRegister
@ZenExpansion("crafttweaker.events.IEventManager")
public class CTTFEventManager {

    public static final EventList<BossConstructionEvent> elBossConstruction = new EventList<>();
    public static final EventList<BossSpawnEvent> elBossSpawn = new EventList<>();

    @ZenMethod
    public static IEventHandle onBossConstruction(IEventManager manager, IEventHandler<BossConstructionEvent> event) {
        return elBossConstruction.add(event);
    }

    @ZenMethod
    public static IEventHandle onBossSpawn(IEventManager manager, IEventHandler<BossSpawnEvent> event) {
        return elBossSpawn.add(event);
    }

    @SubscribeEvent
    public static void onBossConstruction(BossEvent.Construction event) {
        if (CTTFEventManager.elBossConstruction.hasHandlers()) {
            BossConstructionEvent e = new BossConstructionEvent(event);
            CTTFEventManager.elBossConstruction.publish(e);
            event.setBoss(CraftTweakerMC.getEntityLivingBase(e.modifiedBoss));
        }
    }

    @SubscribeEvent
    public static void onBossSpawn(BossEvent.Spawning event) {
        if (CTTFEventManager.elBossSpawn.hasHandlers()) {
            BossSpawnEvent e = new BossSpawnEvent(event);
            CTTFEventManager.elBossSpawn.publish(e);
            event.setResult(Event.Result.valueOf(e.getResult().toUpperCase(Locale.US)));
        }
    }

    static {
        MinecraftForge.EVENT_BUS.register(CTTFEventManager.class);
    }
}
