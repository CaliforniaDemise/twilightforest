package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import twilightforest.TFRegistries;
import twilightforest.entity.MagicPainting;
import twilightforest.entity.MagicPaintingVariant;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.TwilightJigsawPiece;

import java.util.Optional;

public class TowerGallery extends TwilightJigsawPiece implements PieceBeardifierModifier {
	public TowerGallery(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
		super(TFStructurePieceTypes.TOWER_GALLERY.value(), compoundTag, ctx, readSettings(compoundTag));

		TowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(TowerUtil.ROOM_SPAWNERS), true);
	}

	public TowerGallery(int genDepth, StructureTemplateManager structureManager, ResourceLocation templateLocation, JigsawPlaceContext jigsawContext) {
		super(TFStructurePieceTypes.TOWER_GALLERY.value(), genDepth, structureManager, templateLocation, jigsawContext);

		TowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(TowerUtil.ROOM_SPAWNERS), true);
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

	@Override
	protected void processJigsaw(StructurePiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord connection, int jigsawIndex) {
		if ("twilightforest:lich_tower/roof".equals(connection.target())) {
			ResourceLocation fallbackRoof = TowerUtil.rollGalleryRoof(random, this.boundingBox);
			FrontAndTop orientationToMatch = TowerRoom.getVerticalOrientation(connection, Direction.UP, this);
			TowerRoom.tryRoof(pieceAccessor, random, connection, fallbackRoof, orientationToMatch, true, this, this.genDepth + 1, this.structureManager);
		}
	}

	@Override
	protected void handleDataMarker(String label, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBounds) {
		level.removeBlock(pos, false);

		Direction direction = this.placeSettings.getRotation().rotate(Direction.SOUTH);

		Optional<Holder.Reference<MagicPaintingVariant>> variantHolderOpt = variantForGallery(level, this.templateName);
		MagicPainting galleryPainting = TFEntities.MAGIC_PAINTING.value().create(level.getLevel());
		if (variantHolderOpt.isPresent() && galleryPainting != null) {
			galleryPainting.setDirection(direction);
			galleryPainting.setVariant(variantHolderOpt.get());

			Vec3 placePos = paintingPlacePos(variantHolderOpt.get().value(), pos, this.placeSettings.getRotation());
			galleryPainting.moveTo(placePos, 0, 0);

			level.addFreshEntityWithPassengers(galleryPainting);
		}
	}

	private static @NotNull Vec3 paintingPlacePos(MagicPaintingVariant painting, BlockPos pos, Rotation rotation) {
		boolean hasOddWidth = ((painting.width() >> 4) & 1) == 1;

		if (hasOddWidth) {
			Vector3f shift = rotation.rotate(Direction.WEST).step();
			// FIXME Overshooting by a full block instead of half
			return pos.getBottomCenter().add(shift.x * 0.5f, shift.y * 0.5f, shift.z * 0.5f);
		} else {
			return pos.getBottomCenter();
		}
	}

	private static Optional<Holder.Reference<MagicPaintingVariant>> variantForGallery(ServerLevelAccessor level, String roomId) {
		String variantId = switch (roomId) {
			case "twilightforest:lich_tower/gallery/castaway_paradise" -> "twilightforest:castaway_paradise";
			case "twilightforest:lich_tower/gallery/darkness" -> "twilightforest:darkness";
			case "twilightforest:lich_tower/gallery/lucid_lands" -> "twilightforest:lucid_lands";
			case "twilightforest:lich_tower/gallery/the_hostile_paradise" -> "twilightforest:untitled"; // TODO switch to the_hostile_paradise, post-rebase
			default -> null;
		};

		if (variantId == null) return Optional.empty();

		return level.registryAccess().registryOrThrow(TFRegistries.Keys.MAGIC_PAINTINGS).getHolder(ResourceLocation.parse(variantId));
	}

	public static void tryPlaceGallery(RandomSource random, StructurePieceAccessor pieceAccessor, @Nullable ResourceLocation roomId, JigsawRecord connection, TwilightJigsawPiece parent, int newDepth, StructureTemplateManager structureManager, String jigsawLabel) {
		JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(parent, connection.pos(), connection.orientation(), structureManager, roomId, jigsawLabel, random);
		if (placeableJunction != null) {
			StructurePiece room = new TowerGallery(newDepth, structureManager, roomId, placeableJunction);
			pieceAccessor.addPiece(room);
			room.addChildren(parent, pieceAccessor, random);
		}
	}
}
