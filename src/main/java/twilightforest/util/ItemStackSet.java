package twilightforest.util;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public abstract class ItemStackSet extends ObjectOpenCustomHashSet<ItemStack> implements ReloadableSet<ItemStack> {

    private static final Hash.Strategy<ItemStack> ITEMSTACK_STRATEGY = new Hash.Strategy<ItemStack>() {
        @Override
        public int hashCode(ItemStack o) {
            if (o == null || o.isEmpty()) return 0;
            return 31 * o.getItem().hashCode();
        }

        @Override
        public boolean equals(ItemStack a, ItemStack b) {
            if (a == null || b == null) return false;
            if (a.isEmpty() || b.isEmpty()) return false;
            return a.getItem() == b.getItem() && (a.getMetadata() == b.getMetadata() || b.getMetadata() == OreDictionary.WILDCARD_VALUE);
        }
    };

    private boolean shouldReload = false;

    public ItemStackSet() {
        super(ITEMSTACK_STRATEGY);
    }

    @Override
    public void setReload() {
        this.shouldReload = true;
    }

    @Override
    public abstract void reload();

    @Override
    public boolean contains(Object k) {
        if (this.shouldReload) {
            this.clear();
            this.reload();
            this.shouldReload = false;
        }
        return super.contains(k);
    }

    public static final class Default extends ItemStackSet {

        public Default(Collection<ItemStack> stacks) {
            this.addAll(stacks);
        }

        @Override
        public void reload() {}
    }

    public static final class Ore extends ItemStackSet {

        private final String ore;

        public Ore(String ore) {
            this.ore = ore;
            this.setReload();
        }

        @Override
        public void reload() {
            this.addAll(OreDictionary.getOres(this.ore));
        }
    }
}
