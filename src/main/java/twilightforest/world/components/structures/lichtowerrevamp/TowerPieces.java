package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;

// TODO: Move to DataMaps if possible to obtain RegistryAccess
public final class TowerPieces {
	// TODO: more bridges?
	static final ResourceLocation[] CENTER_BRIDGES = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/central_bridge")
	};
	public static final ResourceLocation DIRECT_ATTACHMENT = TwilightForestMod.prefix("lich_tower/no_bridge");
	static final ResourceLocation[] BRIDGES = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/room_bridge_5"),
		TwilightForestMod.prefix("lich_tower/room_bridge_3")
	};
	static final ResourceLocation COBBLESTONE_WALL = TwilightForestMod.prefix("lich_tower/wall_cobble");
	static final ResourceLocation[] BRIDGE_COVERS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/wall_bars"),
		COBBLESTONE_WALL
	};
	static final ResourceLocation[] MOB_BRIDGES = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/bridge_spawner"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_bend"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_inverted"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_ring"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_ropes"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_wide"),
		TwilightForestMod.prefix("lich_tower/bridge_spawner_zag")
	};

	static final ResourceLocation[][] ROOMS = new ResourceLocation[][] {
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/3x3/lookout_fence"),
			TwilightForestMod.prefix("lich_tower/3x3/lookout_glass"),
			TwilightForestMod.prefix("lich_tower/3x3/lookout_tinted")
		},
		new ResourceLocation[] {
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
		},
		new ResourceLocation[] {
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
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/9x9/elbow_junction"),
			TwilightForestMod.prefix("lich_tower/9x9/full_junction"),
			TwilightForestMod.prefix("lich_tower/9x9/straight_junction"),
			TwilightForestMod.prefix("lich_tower/9x9/t_junction"),
			TwilightForestMod.prefix("lich_tower/9x9/archives"),
			TwilightForestMod.prefix("lich_tower/9x9/enchanting_prison"),
			TwilightForestMod.prefix("lich_tower/9x9/keepsake_casket"),
			TwilightForestMod.prefix("lich_tower/9x9/tiered_library")
		}
	};

	static final ResourceLocation[][] ROOFS = new ResourceLocation[][] {
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/3x3/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/slabs_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/fence_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/curved_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/pointed_roof")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/5x5/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/slabs_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/fence_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/curved_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/pointed_roof")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/7x7/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/slabs_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/fence_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/curved_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/pointed_roof")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/9x9/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/slabs_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/fence_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/curved_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/pointed_roof")
		}
	};

	static final ResourceLocation[][] SIDE_ROOFS = new ResourceLocation[][] {
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/3x3/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/slabs_side_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/fence_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/curved_roof"),
			TwilightForestMod.prefix("lich_tower/3x3/pointed_roof")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/5x5/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/slabs_side_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/fence_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/curved_roof"),
			TwilightForestMod.prefix("lich_tower/5x5/pointed_roof")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/7x7/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/slabs_side_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/fence_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/curved_roof"),
			TwilightForestMod.prefix("lich_tower/7x7/pointed_roof")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/9x9/pyramid_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/slabs_side_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/fence_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/curved_roof"),
			TwilightForestMod.prefix("lich_tower/9x9/pointed_roof")
		}
	};

	static final ResourceLocation[][] BEARDS = new ResourceLocation[][] {
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/5x5/beard_fancy"),
			TwilightForestMod.prefix("lich_tower/5x5/beard_fangs"),
			TwilightForestMod.prefix("lich_tower/5x5/beard_plain")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/7x7/beard_fancy"),
			TwilightForestMod.prefix("lich_tower/7x7/beard_fangs"),
			TwilightForestMod.prefix("lich_tower/7x7/beard_plain")
		},
		new ResourceLocation[] {
			TwilightForestMod.prefix("lich_tower/9x9/beard_fancy"),
			TwilightForestMod.prefix("lich_tower/9x9/beard_fangs"),
			TwilightForestMod.prefix("lich_tower/9x9/beard_plain")
		}
	};

	static final ResourceLocation[] FLAT_SIDE_ROOFS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/flat_side_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/flat_side_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/flat_side_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/flat_side_roof")
	};

	static final ResourceLocation[] FLAT_ROOFS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/3x3/flat_roof"),
		TwilightForestMod.prefix("lich_tower/5x5/flat_roof"),
		TwilightForestMod.prefix("lich_tower/7x7/flat_roof"),
		TwilightForestMod.prefix("lich_tower/9x9/flat_roof")
	};

	static final ResourceLocation[] FLAT_BEARDS = new ResourceLocation[] {
		TwilightForestMod.prefix("lich_tower/5x5/beard_flat"),
		TwilightForestMod.prefix("lich_tower/7x7/beard_flat"),
		TwilightForestMod.prefix("lich_tower/9x9/beard_flat")
	};

	private TowerPieces() {
		throw new IllegalStateException("How did we get here?");
	}
}
