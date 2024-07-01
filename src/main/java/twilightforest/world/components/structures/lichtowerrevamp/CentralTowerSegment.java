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
import twilightforest.TwilightForestMod;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public final class CentralTowerSegment extends TwilightJigsawPiece implements PieceBeardifierModifier {
	private final boolean putMobBridge;

	public CentralTowerSegment(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
		super(TFStructurePieceTypes.CENTRAL_TOWER.get(), compoundTag, ctx, readSettings(compoundTag));

		TowerPieces.addDefaultProcessors(this.placeSettings);

		this.putMobBridge = compoundTag.getBoolean("put_bridge");
	}

	public CentralTowerSegment(StructureTemplateManager structureManager, int genDepth, JigsawPlaceContext jigsawContext, boolean putMobBridge) {
		super(TFStructurePieceTypes.CENTRAL_TOWER.get(), genDepth, structureManager, TwilightForestMod.prefix("lich_tower/tower_slice"), jigsawContext);

		TowerPieces.addDefaultProcessors(this.placeSettings);

		this.putMobBridge = putMobBridge;
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
		super.addAdditionalSaveData(ctx, structureTag);

		structureTag.putBoolean("put_bridge", this.putMobBridge);
	}

	public static void putTowerSegment(StructurePieceAccessor pieceAccessor, RandomSource random, BlockPos sourceJigsawPos, FrontAndTop sourceOrientation, TwilightJigsawPiece parent, StructureTemplateManager structureManager, boolean putMobBridge) {
		JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(parent, sourceJigsawPos, sourceOrientation, structureManager, TwilightForestMod.prefix("lich_tower/tower_slice"), "twilightforest:lich_tower/tower_below", random);

		if (placeableJunction == null) return;

		StructurePiece towerBase = new CentralTowerSegment(structureManager, parent.getGenDepth() + 1, placeableJunction, putMobBridge);
		pieceAccessor.addPiece(towerBase);
		towerBase.addChildren(parent, pieceAccessor, random);
	}

	@Override
	protected void processJigsaw(StructurePiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord connection, int jigsawIndex) {
		switch (connection.target()) {
			case "twilightforest:lich_tower/tower_below" -> {
				if (this.genDepth < random.nextInt(4) + 6) {
					CentralTowerSegment.putTowerSegment(pieceAccessor, random, connection.pos(), connection.orientation(), this, this.structureManager, !this.putMobBridge && random.nextInt(3) != 0);
				} else {
					JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(this, connection.pos(), connection.orientation(), this.structureManager, TwilightForestMod.prefix("lich_tower/tower_boss_room"), "twilightforest:lich_tower/tower_below", random);

					if (placeableJunction != null) {
						StructurePiece bossRoom = new BossRoom(this.structureManager, placeableJunction);
						pieceAccessor.addPiece(bossRoom);
						bossRoom.addChildren(this, pieceAccessor, random);
					}
				}
			}
			case "twilightforest:lich_tower/bridge" -> {
				TowerBridge.tryRoomAndBridge(this, pieceAccessor, random, connection.pos(), connection.orientation(), this.structureManager, true, 4 - random.nextInt(2) - (random.nextInt(this.genDepth) >> 1) - (this.genDepth >> 2), false, this.genDepth + 1 + 1);
			}
			case "twilightforest:mob_bridge" -> {
				if (this.putMobBridge) {
					ResourceLocation mobBridgeLocation = TowerPieces.rollMobBridge(random);
					JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(this, connection.pos(), connection.orientation(), this.structureManager, mobBridgeLocation, "twilightforest:mob_bridge", random);

					if (placeableJunction != null) {
						StructurePiece mobBridgePiece = new TowerMobBridge(this.genDepth + 1, this.structureManager, mobBridgeLocation, placeableJunction);
						pieceAccessor.addPiece(mobBridgePiece);
						mobBridgePiece.addChildren(this, pieceAccessor, random);
					}
				}
			}
		}
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
		return TerrainAdjustment.NONE;
	}

	@Override
	public int getGroundLevelDelta() {
		return 0;
	}
}
