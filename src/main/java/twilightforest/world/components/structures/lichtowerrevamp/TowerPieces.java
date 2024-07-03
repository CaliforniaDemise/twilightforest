package twilightforest.world.components.structures.lichtowerrevamp;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFEntities;
import twilightforest.util.ArrayUtil;
import twilightforest.world.components.processors.*;

public final class TowerPieces {
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
		TwilightForestMod.prefix("lich_tower/5x5/t_junction"),
		TwilightForestMod.prefix("lich_tower/5x5/altar"),
		TwilightForestMod.prefix("lich_tower/5x5/desk"),
		TwilightForestMod.prefix("lich_tower/5x5/full_junction_1"),
		TwilightForestMod.prefix("lich_tower/5x5/full_junction_2"),
		TwilightForestMod.prefix("lich_tower/5x5/full_junction_3"),
		TwilightForestMod.prefix("lich_tower/5x5/ladder"),
		TwilightForestMod.prefix("lich_tower/5x5/library"),
		TwilightForestMod.prefix("lich_tower/5x5/lone_chest"),
		TwilightForestMod.prefix("lich_tower/5x5/spawner_1"),
		TwilightForestMod.prefix("lich_tower/5x5/spawner_2"),
		TwilightForestMod.prefix("lich_tower/5x5/spawner_3"),
		TwilightForestMod.prefix("lich_tower/5x5/spawner_4"),
		TwilightForestMod.prefix("lich_tower/5x5/spawner_5"),
		TwilightForestMod.prefix("lich_tower/5x5/webbed_spawner")
	};
	private static final ResourceLocation[] rooms7 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/7x7/elbow_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/full_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/straight_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/t_junction"),
		TwilightForestMod.prefix("lich_tower/7x7/altars"),
		TwilightForestMod.prefix("lich_tower/7x7/book_staircases"),
		TwilightForestMod.prefix("lich_tower/7x7/cactus"),
		TwilightForestMod.prefix("lich_tower/7x7/desk"),
		TwilightForestMod.prefix("lich_tower/7x7/full_junction_1"),
		TwilightForestMod.prefix("lich_tower/7x7/full_junction_2"),
		TwilightForestMod.prefix("lich_tower/7x7/grave"),
		TwilightForestMod.prefix("lich_tower/7x7/library_hall"),
		TwilightForestMod.prefix("lich_tower/7x7/nursery"),
		TwilightForestMod.prefix("lich_tower/7x7/potion"),
		TwilightForestMod.prefix("lich_tower/7x7/ritual"),
		TwilightForestMod.prefix("lich_tower/7x7/tiered_library")
	};
	private static final ResourceLocation[] rooms9 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/9x9/elbow_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/full_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/straight_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/t_junction"),
		TwilightForestMod.prefix("lich_tower/9x9/archives"),
		TwilightForestMod.prefix("lich_tower/9x9/enchanting_prison"),
		TwilightForestMod.prefix("lich_tower/9x9/keepsake_casket"),
		TwilightForestMod.prefix("lich_tower/9x9/tiered_library")
	};

	// TODO: more bridges?
	private static final ResourceLocation[] centerBridges = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/central_bridge")
	};
	public static final ResourceLocation DIRECT_ATTACHMENT = TwilightForestMod.prefix("lich_tower/no_bridge");
	private static final ResourceLocation[] bridges = new ResourceLocation[] {
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
	private static final ResourceLocation[] BEARDS_5 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/beard_fancy"),
		TwilightForestMod.prefix("lich_tower/5x5/beard_fangs"),
		TwilightForestMod.prefix("lich_tower/5x5/beard_plain")
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
	private static final ResourceLocation[] BEARDS_7 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/7x7/beard_fancy"),
		TwilightForestMod.prefix("lich_tower/7x7/beard_fangs"),
		TwilightForestMod.prefix("lich_tower/7x7/beard_plain")
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
	private static final ResourceLocation[] BEARDS_9 = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/9x9/beard_fancy"),
		TwilightForestMod.prefix("lich_tower/9x9/beard_fangs"),
		TwilightForestMod.prefix("lich_tower/9x9/beard_plain")
	};

	private static final ResourceLocation[][] BEARDS = new ResourceLocation[][] {
		BEARDS_5,
		BEARDS_7,
		BEARDS_9
	};

	private static final ResourceLocation[] FLAT_SIDE_ROOFS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/flat_side_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/flat_side_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/flat_side_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/flat_side_roof")
	};
	private static final ResourceLocation[] FLAT_ROOFS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/flat_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/flat_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/flat_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/flat_roof")
	};
	private static final ResourceLocation[] FLAT_BEARDS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/beard_flat"),
		TwilightForestMod.prefix("lich_tower/7x7/beard_flat"),
		TwilightForestMod.prefix("lich_tower/9x9/beard_flat")
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

			return ArrayUtil.safeShuffledCopy(roofList, randomSource);
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

		return ConcatenatedListView.of(ArrayUtil.safeShuffledCopy(tallRoofList, randomSource), ArrayUtil.safeShuffledCopy(roofList, randomSource));
	}

	public static Iterable<ResourceLocation> beards(RandomSource randomSource, int size) {
		return ArrayUtil.safeShuffledCopy(ArrayUtil.orNull(BEARDS, size - 1), randomSource);
	}

	@Nullable
	public static ResourceLocation getFallbackRoof(int size, boolean sideAttachment) {
		if (sideAttachment) {
			return ArrayUtil.orNull(FLAT_SIDE_ROOFS, size);
		} else {
			return ArrayUtil.orNull(FLAT_ROOFS, size);
		}
	}

	@Nullable
	public static ResourceLocation getFallbackBeard(int size) {
		return ArrayUtil.orNull(FLAT_BEARDS, size - 1);
	}

	public static void addDefaultProcessors(StructurePlaceSettings settings) {
		settings.addProcessor(JigsawProcessor.INSTANCE)
			.addProcessor(StoneBricksVariants.INSTANCE)
			.addProcessor(CobbleVariants.INSTANCE)
			.addProcessor(CANDELABRA_MODIFIER);
	}

	private TowerPieces() {
		throw new IllegalStateException("How did we get here?");
	}
}
