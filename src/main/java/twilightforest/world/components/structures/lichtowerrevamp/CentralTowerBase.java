package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

public final class CentralTowerBase extends TwilightJigsawPiece implements PieceBeardifierModifier {
	public CentralTowerBase(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
		super(TFStructurePieceTypes.CENTRAL_TOWER_BASE.get(), compoundTag, ctx, readSettings(compoundTag));

		TowerPieces.addDefaultProcessors(this.placeSettings);
	}

	public CentralTowerBase(StructureTemplateManager structureManager, JigsawPlaceContext jigsawContext) {
		super(TFStructurePieceTypes.CENTRAL_TOWER_BASE.get(), 1, structureManager, TwilightForestMod.prefix("lich_tower/tower_base"), jigsawContext);

		TowerPieces.addDefaultProcessors(this.placeSettings);
	}

	@Override
	protected void processJigsaw(StructurePiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord connection, int jigsawIndex) {
		if ("twilightforest:lich_tower/tower_below".equals(connection.target())) {
			CentralTowerSegment.putTowerSegment(pieceAccessor, random, connection.pos(), connection.orientation(), this, this.structureManager, true);
		} else if ("twilightforest:lich_tower/bridge".equals(connection.target())) {
			TowerBridge.tryRoomAndBridge(this, pieceAccessor, random, connection.pos(), connection.orientation(), this.structureManager, true, 4, true, this.genDepth + 1);
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
		return TerrainAdjustment.BEARD_BOX;
	}

	@Override
	public int getGroundLevelDelta() {
		return 1;
	}
}
