package twilightforest.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import twilightforest.TwilightForestMod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GSPlugin implements GroovyPlugin {

    @GroovyBlacklist
    public static Container instance;

    @Nonnull
    @Override
    public String getModId() {
        return TwilightForestMod.ID;
    }

    @Nullable
    @Override
    public GroovyPropertyContainer createGroovyPropertyContainer() {
        instance = new GSPlugin.Container();
        return instance;
    }

    @Nonnull
    @Override
    public String getContainerName() {
        return TwilightForestMod.NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> groovyContainer) {}

    public static class Container extends GroovyPropertyContainer {

        public final Transformation transformationPowder = new Transformation();
        public final UncraftingTable uncraftingTable = new UncraftingTable();

        public Container() {
            this.addProperty(this.transformationPowder);
            this.addProperty(this.uncraftingTable);
        }
    }
}
