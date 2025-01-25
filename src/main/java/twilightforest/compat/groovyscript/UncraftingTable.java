package twilightforest.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientHelper;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.inventory.ContainerTFUncrafting;
import twilightforest.item.recipe.UncraftingRecipe;
import twilightforest.item.recipe.UncraftingShapedRecipe;
import twilightforest.item.recipe.UncraftingShapelessRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@RegistryDescription(linkGenerator = TwilightForestMod.ID)
public class UncraftingTable extends VirtualizedRegistry<IRecipe> {

    private List<ResourceLocation> recipeList = new ArrayList<>();
    private List<ItemStack> stackList = new ArrayList<>();

    public ShapedRecipe shapedBuilder() {
        return new ShapedRecipe();
    }

    public ShapelessRecipe shapelessBuilder() {
        return new ShapelessRecipe();
    }

    @Override
    public void onReload() {
        removeScripted().forEach(recipe -> ContainerTFUncrafting.removeRecipe(recipe.getRecipeOutput()));
        restoreFromBackup().forEach(ContainerTFUncrafting::addRecipe);
        this.recipeList.forEach(ContainerTFUncrafting::removeRecipeFromList);
        this.stackList.forEach(ContainerTFUncrafting::removeStackFromList);
        this.recipeList = new ArrayList<>();
        this.stackList = new ArrayList<>();
    }

    public UncraftingTable() {
        super(Lists.newArrayList("uncrafting", "Uncrafting", "uncraftingtable", "uncrafting_table", "UncraftingTable"));
    }

    public void addItemToList(IIngredient ingredient) {
        for (ItemStack stack : ingredient.getMatchingStacks()) {
            ContainerTFUncrafting.addStackToList(stack);
            this.stackList.add(stack);
        }
    }

    public void addRecipeToList(ResourceLocation location) {
        ContainerTFUncrafting.addRecipeToList(location);
        this.recipeList.add(location);
    }

    public void addRecipeToList(String location) {
        this.addRecipeToList(new ResourceLocation(location));
    }

    public void add(IRecipe recipe) {
        if (recipe != null) {
            addScripted(recipe);
            ContainerTFUncrafting.addRecipe(recipe);
        }
    }

    public boolean remove(IRecipe recipe) {
        if (recipe != null) {
            IRecipe rec = ContainerTFUncrafting.removeRecipe(recipe);
            if (rec != null) return this.addBackup(rec);
        }
        return false;
    }

    public void addShaped(ItemStack input, int recipeCost, List<List<IIngredient>> outputs) {
        NonNullList<Ingredient> list = NonNullList.create();
        int i = 0;
        for (List<IIngredient> l : outputs) {
            l.forEach(ing -> list.add(ing == null ? Ingredient.EMPTY : ing.toMcIngredient()));
            i++;
        }
        UncraftingRecipe recipe = new UncraftingShapedRecipe(outputs.get(0).size(), i, list, input, recipeCost);
        this.add(recipe);
    }

//    public void addShaped(ItemStack input, int recipeCost, Object... outputs) {
//        for (int i = 0; i < outputs.length; i++) {
//            Object o = outputs[i];
//            if (o instanceof IIngredient) outputs[i] = ((IIngredient) o).toMcIngredient();
//        }
//        CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(outputs);
//        UncraftingRecipe recipe = new UncraftingShapedRecipe(primer.width, primer.height, primer.input, input, recipeCost);
//        this.add(recipe);
//    }

    public void addShapeless(ItemStack input, int recipeCost, List<IIngredient> outputs) {
        NonNullList<Ingredient> list = NonNullList.withSize(outputs.size(), Ingredient.EMPTY);
        for (int i = 0; i < outputs.size(); i++) {
            list.set(i, outputs.get(i).toMcIngredient());
        }
        UncraftingRecipe recipe = new UncraftingShapelessRecipe(input, list, recipeCost);
        this.add(recipe);
    }

    public void removeByInput(ItemStack input) {
        ContainerTFUncrafting.removeRecipe(input).forEach(this::addBackup);
    }

    public void removeAll() {
        ContainerTFUncrafting.removeAll().forEach(this::addBackup);
    }

    public SimpleObjectStream<IRecipe> streamRecipes() {
        return new SimpleObjectStream<>(ContainerTFUncrafting.getAll()).setRemover(this::remove);
    }

    public class ShapedRecipe extends UncraftingBuilder {

        private int width, height;

//        public UncraftingBuilder recipe(Object... outputs) {
//            for (int i = 0; i < outputs.length; i++) {
//                Object o = outputs[i];
//                if (o instanceof IIngredient) outputs[i] = ((IIngredient) o).toMcIngredient();
//            }
//            CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(outputs);
//            this.outputs = primer.input;
//            this.width = primer.width;
//            this.height = primer.height;
//            return this;
//        }
//
//        public UncraftingBuilder outputs(Object... outputs) {
//            return this.recipe(outputs);
//        }

        public UncraftingBuilder recipe(List<List<IIngredient>> outputs) {
            NonNullList<Ingredient> outs = NonNullList.create();
            for (List<IIngredient> l : outputs) {
                l.forEach(ing -> outs.add(ing == null ? Ingredient.EMPTY : ing.toMcIngredient()));
            }
            this.outputs = outs;
            this.width = outputs.get(0).size();
            this.height = outputs.size();
            return this;
        }

        public UncraftingBuilder outputs(List<List<IIngredient>> outputs) {
            return this.recipe(outputs);
        }

        @Override
        protected UncraftingRecipe create() {
            return new UncraftingShapedRecipe(this.width, this.height, this.outputs, this.input, this.cost);
        }
    }

    public class ShapelessRecipe extends UncraftingBuilder {

        public ShapelessRecipe outputs(List<IIngredient> outputs) {
            NonNullList<Ingredient> outs = NonNullList.withSize(outputs.size(), Ingredient.EMPTY);
            for (int i = 0; i < outputs.size(); i++) {
                outs.set(i, outputs.get(i).toMcIngredient());
            }
            this.outputs = outs;
            return this;
        }

        @Override
        protected UncraftingRecipe create() {
            return new UncraftingShapelessRecipe(this.input, this.outputs, this.cost);
        }
    }

    public abstract class UncraftingBuilder {

        protected ItemStack input;
        protected int cost;
        protected NonNullList<Ingredient> outputs;

        public UncraftingBuilder input(ItemStack input) {
            this.input = input;
            return this;
        }

        public UncraftingBuilder cost(int cost) {
            this.cost = cost;
            return this;
        }

        @Nullable
        public UncraftingRecipe register() {
            GroovyLog.Msg msg = GroovyLog.msg("Error adding Twilight Forest Uncrafting recipe");
            if (msg.add(IngredientHelper.isEmpty(this.input), () -> "Input must not be empty")
                    .add(this.outputs == null || this.outputs.isEmpty(), () -> "outputs must not be empty")
                    .add(this.outputs != null && this.outputs.size() > 9, () -> "maximum inputs are " + 9 + " but found " + this.outputs.size())
                    .error()
                    .postIfNotEmpty()) {
                return null;
            }
            UncraftingRecipe recipe = this.create();
            UncraftingTable.this.add(recipe);
            return recipe;
        }

        protected abstract UncraftingRecipe create();
    }

    protected static NonNullList<Ingredient> toIngredientList(List<IIngredient> list) {
        NonNullList<Ingredient> l = NonNullList.withSize(list.size(), Ingredient.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            l.set(i, list.get(i).toMcIngredient());
        }
        return l;
    }
}
