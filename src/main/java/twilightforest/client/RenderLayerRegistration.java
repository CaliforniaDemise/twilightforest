package twilightforest.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import twilightforest.block.TFBlocks;

public class RenderLayerRegistration {
	public static void init() {
		RenderType cutoutMipped = RenderType.cutoutMipped();
		RenderType cutout = RenderType.cutout();
		RenderType translucent = RenderType.translucent();
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TWILIGHT_OAK_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.RAINBOW_OAK_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CANOPY_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MANGROVE_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TIME_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TRANSFORMATION_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MINING_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SORTING_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TIME_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TRANSFORMATION_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SORTING_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TIME_TRAPDOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SORTING_TRAPDOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TWILIGHT_PORTAL.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.AURORALIZED_GLASS.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.THORN_ROSE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FIERY_BLOCK.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.THORN_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BEANSTALK_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.GIANT_LEAVES.get(), cutoutMipped);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.EXPERIMENT_115.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.WISPY_CLOUD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.UBEROUS_SOIL.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TROLLVIDR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.UNRIPE_TROLLBER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TROLLBER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HUGE_LILY_PAD.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HUGE_WATER_LILY.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.PINK_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.YELLOW_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VIOLET_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.PINK_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BLUE_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.GREEN_THORNS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BROWN_THORNS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BURNT_THORNS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.HEDGE_MAZE_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.HOLLOW_HILL_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.QUEST_GROVE_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.MUSHROOM_TOWER_MINIATURE_STRUCTURE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.MINOTAUR_LABYRINTH_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.HYDRA_LAIR_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.GOBLIN_STRONGHOLD_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.DARK_TOWER_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.YETI_CAVE_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.AURORA_PALACE_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.TROLL_CAVE_COTTAGE_MINIATURE_STRUCTURE.get(), cutout);
//		RenderTypeLookup.setRenderLayer(TFBlocks.FINAL_CASTLE_MINIATURE_STRUCTURE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TROPHY_PEDESTAL.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TWISTED_STONE_PILLAR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CANDELABRA.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FIERY_BLOCK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FIREFLY_JAR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FIREFLY_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CICADA_JAR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MOSS_PATCH.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MAYAPPLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CLOVER_PATCH.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FIDDLEHEAD.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MUSHGLOOM.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TORCHBERRY_PLANT.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ROOT_STRAND.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FALLEN_LEAVES.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.UNCRAFTING_TABLE.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ENCASED_SMOKER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ENCASED_FIRE_JET.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TWILIGHT_OAK_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.RAINBOW_OAK_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CANOPY_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MANGROVE_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.DARKWOOD_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HOLLOW_OAK_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TIME_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TRANSFORMATION_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MINING_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SORTING_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BUILT_BLOCK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ANTIBUILT_BLOCK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.REACTOR_DEBRIS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.PINK_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BLUE_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.GREEN_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VIOLET_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ORANGE_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.NAGA_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.LICH_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.HYDRA_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.UR_GHAST_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SNOW_QUEEN_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.MINOSHROOM_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ALPHA_YETI_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.REAPPEARING_BLOCK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.LOCKED_VANISHING_BLOCK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VANISHING_BLOCK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CARMINITE_BUILDER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ANTIBUILDER.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CARMINITE_REACTOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.GHAST_TRAP.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_CANOPY_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_MANGROVE_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_DARKWOOD_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_HOLLOW_OAK_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_RAINBOW_OAK_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_TIME_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_TRANSFORMATION_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_MINING_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_SORTING_SAPLING.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_MAYAPPLE.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_FIDDLEHEAD.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_MUSHGLOOM.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_THORN.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_GREEN_THORN.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.POTTED_DEAD_THORN.get(), cutout);
	}
}
