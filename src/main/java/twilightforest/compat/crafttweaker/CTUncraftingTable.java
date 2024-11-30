package twilightforest.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import twilightforest.inventory.ContainerTFUncrafting;
import twilightforest.item.recipe.UncraftingShapedRecipe;
import twilightforest.item.recipe.UncraftingShapelessRecipe;

@ZenRegister
@ZenClass("mods.twilightforest.UncraftingTable")
public class CTUncraftingTable {

    @ZenMethod
    public static void addStackToList(IIngredient ingredient) {
        for (IItemStack stack : ingredient.getItemArray()) {
            ContainerTFUncrafting.addStackToList(CraftTweakerMC.getItemStack(stack));
        }
    }

    @ZenMethod
    public static void addRecipeToList(String name) {
        ContainerTFUncrafting.addRecipeToList(name);
    }

    @ZenMethod
    public static void addShaped(IItemStack input, int cost, IIngredient[][] outputs) {
        if (outputs.length == 0) return;
        int width = outputs[0].length;
        int height = outputs.length;
        NonNullList<Ingredient> ins = NonNullList.create();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                ins.add(CraftTweakerMC.getIngredient(outputs[h][w]));
            }
        }
        ItemStack in = CraftTweakerMC.getItemStack(input);
        ContainerTFUncrafting.addRecipe(in, new UncraftingShapedRecipe(width, height, ins, in, cost));
    }

    @ZenMethod
    public static void addShapeless(IItemStack input, int cost, IIngredient[] inputs) {
        if (inputs.length == 0) return;
        NonNullList<Ingredient> ins = NonNullList.create();
        for (IIngredient ing : inputs) {
            ins.add(CraftTweakerMC.getIngredient(ing));
        }
        ItemStack in = CraftTweakerMC.getItemStack(input);
        ContainerTFUncrafting.addRecipe(in, new UncraftingShapelessRecipe(in, ins, cost));
    }
}
