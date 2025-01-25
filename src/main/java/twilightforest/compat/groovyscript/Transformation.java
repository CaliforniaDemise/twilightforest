package twilightforest.compat.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.documentation.annotations.Example;
import com.cleanroommc.groovyscript.api.documentation.annotations.RecipeBuilderDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RecipeBuilderMethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.RegistryDescription;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.oredict.OreDictionary;
import twilightforest.TwilightForestMod;
import twilightforest.item.ItemTFTransformPowder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RegistryDescription(linkGenerator = TwilightForestMod.ID)
public class Transformation extends VirtualizedRegistry<Transformation.TransformationRecipe> {

    @RecipeBuilderDescription(example = {
            @Example(".input(entity('minecraft:mooshroom')).output('minecraft:cow')"),
            @Example(".input(resource('minecraft:polar_bear'), resource('minecraft:rabbit').twoWay()")
    })
    public EntityRecipeBuilder entityBuilder() {
        return new EntityRecipeBuilder();
    }

    @RecipeBuilderDescription(example = {
            @Example(".input(block('minecraft:stone')).output(blockstate('minecraft:dirt'))"),
            @Example(".input(block('minecraft:iron_block')).output(block('minecraft:gold_block'))"),
            @Example(".input(item('minecraft:planks')).predicate(s -> s.getBlock().getMetaFromState(s) == 0).output(item('minecraft:planks:1'))"),
            @Example(".input(item('minecraft:planks')).predicate(3, 4).output(item('minecraft:planks:3'))"),
            @Example(".input(item('minecraft:planks')).predicate(blockstate('minecraft:planks:5'), blockstate('minecraft:planks:6')).output(item('minecraft:planks:4'))")
    })
    public BlockRecipeBuilder blockBuilder() {
        return new BlockRecipeBuilder();
    }

    @Override
    @GroovyBlacklist
    public void onReload() {
        removeScripted().forEach(TransformationRecipe::onRemove);
        restoreFromBackup().forEach(TransformationRecipe::onRestore);
    }

    public Transformation() {
        super(Lists.newArrayList("transformation", "Transformation", "transformationpowder", "transformation_powder", "TransformationPowder"));
    }

    public void add(TransformationRecipe recipe) {
        if (recipe != null) {
            addScripted(recipe);
            recipe.onRestore();
        }
    }

    public boolean remove(TransformationRecipe recipe) {
        if (recipe != null) {
            addBackup(recipe);
            return recipe.onRemove();
        }
        return false;
    }

    public void addEntity(EntityEntry input, EntityEntry output) {
        ResourceLocation in = input.getRegistryName();
        ResourceLocation out = output.getRegistryName();
        if (in == null || out == null) return;
        this.addEntity(in, out);
    }

    public void addEntity(ResourceLocation input, ResourceLocation output) {
        TransformationRecipe recipe = new EntityTransformation(input, output, false);
        this.add(recipe);
    }

    public void addBlock(Block input, Predicate<IBlockState> inputPredicate, IBlockState output) {
        TransformationRecipe recipe = new BlockTransformation(input, inputPredicate, output);
        this.add(recipe);
    }

    public void addBlock(Block input, IBlockState output) {
        this.addBlock(input, null, output);
    }

    public void addBlock(Block input, IBlockState output, int... metas) {
        this.addBlock(input, s -> {
            for (int m : metas) {
                if (output.getBlock().getMetaFromState(output) == m) return true;
            }
            return false;
        }, output);
    }

    public void addBlock(IBlockState input, IBlockState output) {
        this.addBlock(input.getBlock(), s -> s == input, output);
    }

    public void addBlock(ItemStack input, Predicate<IBlockState> inputPredicate, ItemStack output) {
        Block iBlock = getBlock(input);
        Block oBlock = getBlock(output);
        if (iBlock == Blocks.AIR || oBlock == Blocks.AIR) return;
        IBlockState state = oBlock.getStateFromMeta(output.getItem().getMetadata(output.getMetadata()));
        this.addBlock(iBlock, inputPredicate, state);
    }

    public void addBlock(ItemStack input, ItemStack output) {
        this.addBlock(input, s -> input.getMetadata() == OreDictionary.WILDCARD_VALUE || s.getBlock().getMetaFromState(s) == input.getMetadata(), output);
    }

    public void addBlock(ItemStack input, ItemStack output, int... metas) {
        this.addBlock(input, s -> {
            for (int m : metas) {
                if (output.getItem().getMetadata(output.getMetadata()) == m) return true;
            }
            return false;
        }, output);
    }

    public void addTwoWayEntity(EntityEntry input, EntityEntry output) {
        ResourceLocation in = input.getRegistryName();
        ResourceLocation out = output.getRegistryName();
        if (in == null || out == null) return;
        this.addTwoWayEntity(in, out);
    }

    public void addTwoWayEntity(ResourceLocation input, ResourceLocation output) {
        TransformationRecipe recipe = new EntityTransformation(input, output, true);
        this.add(recipe);
    }

    public boolean removeEntity(EntityEntry input) {
        ResourceLocation in = input.getRegistryName();
        if (in == null) return false;
        return this.removeEntity(in);
    }

    public boolean removeEntity(ResourceLocation input) {
        ResourceLocation out = ItemTFTransformPowder.getTransformationEntity(input);
        TransformationRecipe recipe = new EntityTransformation(input, out, false);
        return this.remove(recipe);
    }

    public boolean removeTwoWayEntity(EntityEntry input) {
        ResourceLocation in = input.getRegistryName();
        if (in == null) return false;
        return this.removeTwoWayEntity(in);
    }

    public boolean removeTwoWayEntity(ResourceLocation input) {
        ResourceLocation out = ItemTFTransformPowder.getTransformationEntity(input);
        TransformationRecipe recipe = new EntityTransformation(input, out, true);
        return this.remove(recipe);
    }

    public void removeAll() {
        List<TransformationRecipe> recipes = new ArrayList<>(ItemTFTransformPowder.getTransformMap().size());
        ItemTFTransformPowder.getTransformMap().forEach((key, value) -> recipes.add(new EntityTransformation(key, value, false)));
        recipes.forEach(this::remove);
    }

    public SimpleObjectStream<TransformationRecipe> streamRecipes() {
        return new SimpleObjectStream<>(getAllRecipes()).setRemover(this::remove);
    }

    private static List<TransformationRecipe> getAllRecipes() {
        List<TransformationRecipe> recipes = new ArrayList<>(ItemTFTransformPowder.getTransformMap().size() + ItemTFTransformPowder.getTransformBlockMap().size());
        ItemTFTransformPowder.getTransformMap().forEach((k, v) -> recipes.add(new EntityTransformation(k, v, false)));
        ItemTFTransformPowder.getTransformBlockMap().forEach((k, v) -> recipes.add(new BlockTransformation(k, v.getKey(), v.getValue())));
        return recipes;
    }

    protected static Block getBlock(ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock) return ((ItemBlock) stack.getItem()).getBlock();
        else if (stack.getItem() instanceof ItemBlockSpecial) return ((ItemBlock) stack.getItem()).getBlock();
        else return Block.getBlockFromItem(stack.getItem());
    }

    public static class EntityRecipeBuilder extends AbstractRecipeBuilder<EntityTransformation> {

        private ResourceLocation input, output;
        private boolean twoWay = false;

        @Override
        public String getRecipeNamePrefix() {
            return "twilight_forest_transformation_";
        }

        @RecipeBuilderMethodDescription(field = "input")
        public EntityRecipeBuilder setInput(ResourceLocation input) {
            this.input = input;
            return this;
        }

        @RecipeBuilderMethodDescription(field = "input")
        public EntityRecipeBuilder setInput(EntityEntry input) {
            return this.setInput(input.getRegistryName());
        }

        @RecipeBuilderMethodDescription(field = "output")
        public EntityRecipeBuilder setOutput(ResourceLocation output) {
            this.output = output;
            return this;
        }

        @RecipeBuilderMethodDescription(field = "output")
        public EntityRecipeBuilder setOutput(EntityEntry output) {
            return this.setOutput(output.getRegistryName());
        }

        @RecipeBuilderMethodDescription(field = "twoWay")
        public EntityRecipeBuilder setTwoWay() {
            this.twoWay = true;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding Twilight Forest entity transformation recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {}

        @Nullable
        @Override
        public EntityTransformation register() {
            if (this.input == null || this.output == null) return null;
            EntityTransformation transformation = new EntityTransformation(this.input, this.output, this.twoWay);
            GSPlugin.instance.transformationPowder.add(transformation);
            return transformation;
        }
    }

    public static class BlockRecipeBuilder extends AbstractRecipeBuilder<BlockTransformation> {

        private Block input;
        private Predicate<IBlockState> predicate;
        private IBlockState output;

        @RecipeBuilderMethodDescription(field = "input")
        public BlockRecipeBuilder setInput(Block input) {
            this.input = input;
            return this;
        }

        @RecipeBuilderMethodDescription(field = "input")
        public BlockRecipeBuilder setInput(ItemStack input) {
            Block block = Transformation.getBlock(input);
            if (block == Blocks.AIR) return this;
            this.input = block;
            if (input.getMetadata() == OreDictionary.WILDCARD_VALUE) this.predicate = null;
            else this.predicate = s -> s.getBlock().getMetaFromState(s) == input.getMetadata();
            return this;
        }

        @RecipeBuilderMethodDescription(field = "output")
        public BlockRecipeBuilder setOutput(IBlockState output) {
            this.output = output;
            return this;
        }

        @RecipeBuilderMethodDescription(field = "output")
        public BlockRecipeBuilder setOutput(Block output) {
            this.output = output.getDefaultState();
            return this;
        }

        @RecipeBuilderMethodDescription(field = "output")
        public BlockRecipeBuilder setOutput(ItemStack output) {
            Block block = Transformation.getBlock(output);
            if (block == Blocks.AIR) return this;
            this.output = block.getStateFromMeta(output.getItem().getMetadata(output));
            return this;
        }

        @RecipeBuilderMethodDescription(field = "predicate")
        public BlockRecipeBuilder setPredicate(Predicate<IBlockState> predicate) {
            this.predicate = predicate;
            return this;
        }

        @RecipeBuilderMethodDescription(field = "predicate")
        public BlockRecipeBuilder setPredicate(IBlockState... allowedStates) {
            this.predicate = s -> { for (IBlockState state : allowedStates) { if (state == s) return true; } return false; };
            return this;
        }

        public BlockRecipeBuilder addPredicate(IBlockState... allowedStates) {
            Predicate<IBlockState> addPredicate = s -> { for (IBlockState state : allowedStates) { if (state == s) return true; } return false; };
            if (this.predicate == null) this.predicate = addPredicate;
            else this.predicate = this.predicate.or(addPredicate);
            return this;
        }

        @RecipeBuilderMethodDescription(field = "predicate")
        public BlockRecipeBuilder setPredicate(int... metadatas) {
            this.predicate = s -> { for (int meta : metadatas) { if (s.getBlock().getMetaFromState(s) == meta) return true; } return false; };
            return this;
        }

        public BlockRecipeBuilder addPredicate(int... metadatas) {
            Predicate<IBlockState> addPredicate = s -> { for (int meta : metadatas) { if (s.getBlock().getMetaFromState(s) == meta) return true; } return false; };
            if (this.predicate == null) this.predicate = addPredicate;
            else this.predicate = this.predicate.or(addPredicate);
            return this;
        }

        public BlockRecipeBuilder setPredicateSpecific() {
            this.predicate = s -> s.getBlock().getMetaFromState(s) == 0;
            return this;
        }

        @Override
        public String getErrorMsg() {
            return "Error adding Twilight Forest block transformation recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {}

        @Nullable
        @Override
        public BlockTransformation register() {
            if (this.input == null || this.output == null) return null;
            BlockTransformation transformation = new BlockTransformation(this.input, this.predicate, this.output);
            GSPlugin.instance.transformationPowder.add(transformation);
            return transformation;
        }
    }

    public static abstract class TransformationRecipe {
        public abstract void onRestore();
        public abstract boolean onRemove();
    }

    public static class EntityTransformation extends TransformationRecipe {

        private final ResourceLocation input, output;
        private final boolean twoWay;

        public EntityTransformation(ResourceLocation input, ResourceLocation output, boolean twoWay) {
            this.input = input;
            this.output = output;
            this.twoWay = twoWay;
        }

        @Override
        public void onRestore() {
            if (this.twoWay) ItemTFTransformPowder.addTwoWayTransformation(this.input, this.output);
            else ItemTFTransformPowder.addOneWayTransformation(this.input, this.output);
        }

        @Override
        public boolean onRemove() {
            if (this.twoWay) {
                boolean out = true;
                out &= ItemTFTransformPowder.removeEntityTransformation(this.input);
                out &= ItemTFTransformPowder.removeEntityTransformation(this.output);
                return out;
            }
            else return ItemTFTransformPowder.removeEntityTransformation(this.input);
        }
    }

    public static class BlockTransformation extends TransformationRecipe {

        private final Block block;
        private final Predicate<IBlockState> predicate;
        private final IBlockState state;

        public BlockTransformation(Block block, Predicate<IBlockState> predicate, IBlockState state) {
            this.block = block;
            this.predicate = predicate;
            this.state = state;
        }

        @Override
        public void onRestore() {
            if (this.predicate != null) ItemTFTransformPowder.addBlockTransformation(this.block, this.predicate, this.state);
            else ItemTFTransformPowder.addBlockTransformation(this.block, this.state);
        }

        @Override
        public boolean onRemove() {
            return ItemTFTransformPowder.getTransformBlockMap().remove(this.block) != null;
        }
    }
}
