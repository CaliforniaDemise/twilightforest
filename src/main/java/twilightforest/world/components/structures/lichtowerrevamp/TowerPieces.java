package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.processors.CobbleVariants;
import twilightforest.world.components.processors.JigsawProcessor;
import twilightforest.world.components.processors.StoneBricksVariants;

import java.util.List;

public final class TowerPieces {
	// FIXME: Move to DataMap of int -> ResourceLocation[] if possible to obtain RegistryAccess
	private static final ResourceLocation[] thinTowers = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/lookout_fence"),
		TwilightForestMod.prefix("lich_tower/3x3/lookout_glass"),
		TwilightForestMod.prefix("lich_tower/3x3/lookout_tinted")
	};
	private static final ResourceLocation[] rooms5 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/elbow_junction"),
		TwilightForestMod.prefix("lich_tower/5x5/full_junction"),
		TwilightForestMod.prefix("lich_tower/5x5/straight_junction"),
		TwilightForestMod.prefix("lich_tower/5x5/t_junction")
	};
	private static final ResourceLocation[] rooms7 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/7x7/elbow_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/full_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/straight_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/t_junction")
	};
	private static final ResourceLocation[] rooms9 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/9x9/elbow_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/full_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/straight_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/t_junction")
	};

	// TODO: more bridges?
	private static final ResourceLocation[] centerBridges = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/central_bridge")
	};
	private static final ResourceLocation[] bridges = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/no_bridge"),
		TwilightForestMod.prefix("lich_tower/room_bridge_5"),
		TwilightForestMod.prefix("lich_tower/room_bridge_3")
	};
	private static final ResourceLocation COBBLESTONE_WALL = TwilightForestMod.prefix("lich_tower/wall_cobble");
	private static final ResourceLocation[] bridgeCovers = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/wall_bars"),
		COBBLESTONE_WALL
	};
	private static final ResourceLocation[] mobBridges = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/bridge_spawner"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_bend"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_inverted"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_ring"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_ropes"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_wide"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_zag")
	};

	private static final ResourceLocation[] roofs3 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/pyramid_roof"),
		TwilightForestMod.prefix("lich_tower/3x3/slabs_roof"),
		TwilightForestMod.prefix("lich_tower/3x3/fence_roof")
	};
	private static final ResourceLocation[] tallRoofs3 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/curved_roof"),
		TwilightForestMod.prefix("lich_tower/3x3/pointed_roof")
	};
	private static final ResourceLocation[] sideRoofs3 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/curved_side_roof"),
		TwilightForestMod.prefix("lich_tower/3x3/fence_roof"),
		TwilightForestMod.prefix("lich_tower/3x3/slabs_side_roof")
	};

	private static final ResourceLocation[] roofs5 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/pyramid_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/slabs_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/fence_roof")
	};
	private static final ResourceLocation[] tallRoofs5 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/curved_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/pointed_roof")
	};
	private static final ResourceLocation[] sideRoofs5 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/curved_side_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/fence_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/slabs_side_roof")
	};

	private static final ResourceLocation[] roofs7 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/7x7/pyramid_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/slabs_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/fence_roof")
	};
	private static final ResourceLocation[] tallRoofs7 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/7x7/curved_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/pointed_roof")
	};
	private static final ResourceLocation[] sideRoofs7 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/7x7/curved_side_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/fence_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/slabs_side_roof")
	};

	private static final ResourceLocation[] roofs9 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/9x9/pyramid_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/slabs_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/fence_roof")
	};
	private static final ResourceLocation[] tallRoofs9 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/9x9/curved_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/pointed_roof")
	};
	private static final ResourceLocation[] sideRoofs9 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/9x9/curved_side_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/fence_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/slabs_side_roof")
	};

	public static Iterable<ResourceLocation> bridges(boolean fromCentralTower, RandomSource randomSource) {
		return Util.shuffledCopy(fromCentralTower ? centerBridges : bridges, randomSource);
	}

	@Nullable
	public static ResourceLocation getARoom(RandomSource randomSource, int size) {
		ResourceLocation[] roomList = switch(size) {
			case 0 -> thinTowers;
			case 1 -> rooms5;
			case 2 -> rooms7;
			case 3 -> rooms9;
			default -> null;
		};

		if (roomList == null || roomList.length == 0) {
			return null;
		} else {
			return Util.getRandom(roomList, randomSource);
		}
	}

	public static ResourceLocation rollMobBridge(RandomSource randomSource) {
		return Util.getRandom(mobBridges, randomSource);
	}

	public static ResourceLocation rollCover(RandomSource randomSource) {
		return Util.getRandom(bridgeCovers, randomSource);
	}

	public static Iterable<ResourceLocation> roofs(RandomSource randomSource, int size, boolean doSideRoofOnly) {
		if (doSideRoofOnly) {
			@Nullable ResourceLocation[] roofList = switch(size) {
				case 0 -> sideRoofs3;
				case 1 -> sideRoofs5;
				case 2 -> sideRoofs7;
				case 3 -> sideRoofs9;
				default -> null;
			};

			return safeShuffledCopy(roofList, randomSource);
		}

		@Nullable ResourceLocation[] tallRoofList = switch(size) {
			case 0 -> tallRoofs3;
			case 1 -> tallRoofs5;
			case 2 -> tallRoofs7;
			case 3 -> tallRoofs9;
			default -> null;
		};

		@Nullable ResourceLocation[] roofList = switch(size) {
			case 0 -> roofs3;
			case 1 -> roofs5;
			case 2 -> roofs7;
			case 3 -> roofs9;
			default -> null;
		};

		return ConcatenatedListView.of(safeShuffledCopy(tallRoofList, randomSource), safeShuffledCopy(roofList, randomSource));
	}

	public static ResourceLocation getFallbackRoof(int size, boolean sideAttachment) {
		if (sideAttachment) {
			return switch (size) {
				case 3 -> TwilightForestMod.prefix("lich_tower/9x9/flat_side_roof");
				case 2 -> TwilightForestMod.prefix("lich_tower/7x7/flat_side_roof");
				case 1 -> TwilightForestMod.prefix("lich_tower/5x5/flat_side_roof");
				default -> TwilightForestMod.prefix("lich_tower/3x3/flat_side_roof");
			};
		} else {
			return switch (size) {
				case 3 -> TwilightForestMod.prefix("lich_tower/9x9/flat_roof");
				case 2 -> TwilightForestMod.prefix("lich_tower/7x7/flat_roof");
				case 1 -> TwilightForestMod.prefix("lich_tower/5x5/flat_roof");
				default -> TwilightForestMod.prefix("lich_tower/3x3/flat_roof");
			};
		}
	}

	public static <T> List<T> safeShuffledCopy(@Nullable T[] array, RandomSource random) {
		return array == null ? List.of() : Util.shuffledCopy(array, random);
	}

	public static void addDefaultProcessors(StructurePlaceSettings acceptor) {
		acceptor.addProcessor(JigsawProcessor.INSTANCE);
		acceptor.addProcessor(StoneBricksVariants.INSTANCE);
		acceptor.addProcessor(CobbleVariants.INSTANCE);
	}

	private TowerPieces() {
		throw new IllegalStateException("How did we get here?");
	}
}
