package twilightforest.world.components.structures.lichtowerrevamp;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.util.ArrayUtil;
import twilightforest.world.components.processors.*;

public final class TowerUtil {
	public static final StructureProcessor CANDELABRA_MODIFIER = CandelabraProcessor.INSTANCE;
	public static final StructureProcessor ROOM_SPAWNERS = SpawnerProcessor.compile(1, Object2IntMaps.unmodifiable(Util.make(new Object2IntArrayMap<>(), map -> {
		// 1/3 chance for any spider variant, 1/3 chance for skeleton, 1/3 chance for zombie
		map.put(EntityType.SPIDER, 1);
		map.put(EntityType.CAVE_SPIDER, 1);
		map.put(TFEntities.SWARM_SPIDER.get(), 1);
		map.put(TFEntities.HEDGE_SPIDER.get(), 1);
		map.put(EntityType.SKELETON, 4);
		map.put(EntityType.ZOMBIE, 4);
	})));
	public static final StructureProcessor CENTRAL_SPAWNERS = SpawnerProcessor.compile(1, Object2IntMaps.unmodifiable(Util.make(new Object2IntArrayMap<>(), map -> {
		map.put(EntityType.SKELETON, 2);
		map.put(EntityType.ZOMBIE, 1);
		map.put(TFEntities.SWARM_SPIDER.get(), 1);
	})));
	public static final StructureProcessor UPDATE_MARKER = UpdateMarkingProcessor.forBlocks(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL, TFBlocks.WROUGHT_IRON_FENCE.value());

	@Nullable
	public static ResourceLocation rollRandomRoom(RandomSource randomSource, int size) {
		return ArrayUtil.randomOrNull(ArrayUtil.orNull(TowerPieces.ROOMS, size), randomSource);
	}

	public static ResourceLocation rollRandomMobBridge(RandomSource randomSource) {
		return Util.getRandom(TowerPieces.MOB_BRIDGES, randomSource);
	}

	public static ResourceLocation rollRandomCover(RandomSource randomSource) {
		return Util.getRandom(TowerPieces.BRIDGE_COVERS, randomSource);
	}

	public static Iterable<ResourceLocation> shuffledBridges(boolean fromCentralTower, RandomSource randomSource) {
		return Util.shuffledCopy(fromCentralTower ? TowerPieces.CENTER_BRIDGES : TowerPieces.BRIDGES, randomSource);
	}

	public static Iterable<ResourceLocation> shuffledRoofs(RandomSource randomSource, int size, boolean doSideRoofOnly) {
		return ArrayUtil.safeShuffledCopy(ArrayUtil.orNull(doSideRoofOnly ? TowerPieces.SIDE_ROOFS : TowerPieces.ROOFS, size), randomSource);
	}

	public static Iterable<ResourceLocation> shuffledBeards(RandomSource randomSource, int size) {
		return ArrayUtil.safeShuffledCopy(ArrayUtil.orNull(TowerPieces.BEARDS, size - 1), randomSource);
	}

	@Nullable
	public static ResourceLocation getFallbackRoof(int size, boolean sideAttachment) {
		return ArrayUtil.orNull(sideAttachment ? TowerPieces.FLAT_SIDE_ROOFS : TowerPieces.FLAT_ROOFS, size);
	}

	@Nullable
	public static ResourceLocation getFallbackBeard(int size) {
		return ArrayUtil.orNull(TowerPieces.FLAT_BEARDS, size - 1);
	}

	public static void addDefaultProcessors(StructurePlaceSettings settings) {
		settings.addProcessor(JigsawProcessor.INSTANCE)
			.addProcessor(StoneBricksVariants.INSTANCE)
			.addProcessor(CobbleVariants.INSTANCE)
			.addProcessor(CANDELABRA_MODIFIER)
			.addProcessor(UPDATE_MARKER);
	}

	private TowerUtil() {
		throw new IllegalStateException("How did we get here?");
	}
}
