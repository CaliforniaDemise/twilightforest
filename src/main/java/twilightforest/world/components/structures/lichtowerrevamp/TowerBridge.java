package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.FrontAndTop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public final class TowerBridge extends TwilightJigsawPiece implements PieceBeardifierModifier {
	private final boolean generateGround;

	public TowerBridge(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
		super(TFStructurePieceTypes.TOWER_BRIDGE.get(), compoundTag, ctx, readSettings(compoundTag));

		TowerUtil.addDefaultProcessors(this.placeSettings, true);

		this.generateGround = compoundTag.getBoolean("gen_ground");
	}

	public TowerBridge(StructureTemplateManager structureManager, int genDepth, JigsawPlaceContext jigsawContext, ResourceLocation templateLocation, boolean generateGround) {
		super(TFStructurePieceTypes.TOWER_BRIDGE.get(), genDepth, structureManager, templateLocation, jigsawContext);

		TowerUtil.addDefaultProcessors(this.placeSettings, true);

		this.generateGround = generateGround; // Only true for bridge entryway covers on the ground
	}

	@Override
	protected void processJigsaw(StructurePiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord record, int jigsawIndex) {
	}

	@Override
	protected void handleDataMarker(String label, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource random, BoundingBox boundingBox) {
	}

	@Override
	public BoundingBox getBeardifierBox() {
		return this.boundingBox;
	}

	@Override
	public TerrainAdjustment getTerrainAdjustment() {
		return this.generateGround ? TerrainAdjustment.BEARD_THIN : TerrainAdjustment.NONE;
	}

	@Override
	public int getGroundLevelDelta() {
		return 1;
	}

	public static void tryRoomAndBridge(TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, BlockPos sourceJigsawPos, FrontAndTop sourceOrientation, StructureTemplateManager structureManager, boolean fromCentralTower, int roomMaxSize, boolean generateGround, int newDepth, boolean magicGallery) {
		boolean shouldGenerateGround = generateGround && sourceJigsawPos.getY() < 6;
		if (fromCentralTower || random.nextInt((newDepth >> 1) + 1) > 2) {
			for (ResourceLocation bridgeId : TowerUtil.shuffledBridges(fromCentralTower, random)) {
				if (tryBridge(parent, pieceAccessor, random, sourceJigsawPos, sourceOrientation, structureManager, fromCentralTower, roomMaxSize, shouldGenerateGround, newDepth, bridgeId, fromCentralTower, magicGallery)) {
					return;
				}
			}
		}

		// This here is reached only if a room was not successfully generated - now a wall must be placed to cover where the bridge would have been
		if (fromCentralTower) {
			return; // Don't generate covers nor direct side-tower attachments from the central tower
		}

		if (tryBridge(parent, pieceAccessor, random, sourceJigsawPos, sourceOrientation, structureManager, false, roomMaxSize, shouldGenerateGround, newDepth, TowerPieces.DIRECT_ATTACHMENT, true, false)) {
			return;
		}

		putCover(parent, pieceAccessor, random, sourceJigsawPos, sourceOrientation, structureManager, shouldGenerateGround, newDepth);
	}

	private static boolean tryBridge(TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, BlockPos sourceJigsawPos, FrontAndTop sourceOrientation, StructureTemplateManager structureManager, boolean fromCentralTower, int roomMaxSize, boolean generateGround, int newDepth, ResourceLocation bridgeId, boolean allowClipping, boolean magicGallery) {
		JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(parent, sourceJigsawPos, sourceOrientation, structureManager, bridgeId, fromCentralTower ? "twilightforest:lich_tower/bridge_center" : "twilightforest:lich_tower/bridge", random);

		if (placeableJunction != null) {
			TowerBridge bridge = new TowerBridge(structureManager, newDepth, placeableJunction, bridgeId, false);

			if ((allowClipping || pieceAccessor.findCollisionPiece(bridge.boundingBox) == null) && bridge.tryGenerateRoom(random, pieceAccessor, roomMaxSize, generateGround, fromCentralTower, magicGallery)) {
				// If the bridge & room can be fitted, then also add bridge to list then exit this function
				pieceAccessor.addPiece(bridge);
				bridge.addChildren(parent, pieceAccessor, random);
				return true;
			}
		}
		return false;
	}

	public static void putCover(TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, BlockPos sourceJigsawPos, FrontAndTop sourceOrientation, StructureTemplateManager structureManager, boolean generateGround, int newDepth) {
		ResourceLocation bridgeCoverLocation = TowerUtil.rollRandomCover(random);
		JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(parent, sourceJigsawPos, sourceOrientation, structureManager, bridgeCoverLocation, "twilightforest:lich_tower/bridge", random);

		if (placeableJunction != null) {
			StructurePiece bridgeCoverPiece = new TowerBridge(structureManager, newDepth, placeableJunction, bridgeCoverLocation, generateGround);
			pieceAccessor.addPiece(bridgeCoverPiece);
			bridgeCoverPiece.addChildren(parent, pieceAccessor, random);
		}
	}

	public boolean tryGenerateRoom(final RandomSource random, final StructurePieceAccessor structureStart, final int roomMaxSize, boolean generateGround, boolean fromCentralTower, boolean magicGallery) {
		int minSize = (fromCentralTower || generateGround) ? 1 : 0;
		for (JigsawRecord generatingPoint : this.getSpareJigsaws()) {
			if (magicGallery) {
				boolean roomSuccess = this.tryPlaceRoom(random, structureStart, TowerUtil.rollMagicGallery(random), generatingPoint, 0, generateGround, true);

				if (roomSuccess) {
					return true;
				}
			} else {
				for (int roomSize = Math.max(0, roomMaxSize - 1); roomSize >= minSize; roomSize--) {
					boolean roomSuccess = this.tryPlaceRoom(random, structureStart, TowerUtil.rollRandomRoom(random, roomSize), generatingPoint, roomSize, generateGround, false);

					if (roomSuccess) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean tryPlaceRoom(RandomSource random, StructurePieceAccessor pieceAccessor, @Nullable ResourceLocation roomId, JigsawRecord connection, int roomSize, boolean canPutGround, boolean allowClipping) {
		if (roomId != null) {
			JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(this, connection.pos(), connection.orientation(), this.structureManager, roomId, "twilightforest:lich_tower/room", random);

			if (placeableJunction == null) {
				return false;
			}

			boolean generateGround = canPutGround && connection.pos().getY() < 4;

			StructurePiece room = new TowerRoom(this.structureManager, this.genDepth + 1, placeableJunction, roomId, roomSize, generateGround);

			if (allowClipping || pieceAccessor.findCollisionPiece(room.getBoundingBox()) == null) {
				pieceAccessor.addPiece(room);
				room.addChildren(this, pieceAccessor, random);

				return true;
			}
		}

		return false;
	}
}
