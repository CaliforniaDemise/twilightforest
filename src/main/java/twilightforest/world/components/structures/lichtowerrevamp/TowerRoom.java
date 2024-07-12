package twilightforest.world.components.structures.lichtowerrevamp;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.monster.DeathTome;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.DirectionUtil;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.util.jigsaw.JigsawUtil;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public final class TowerRoom extends TwilightJigsawPiece implements PieceBeardifierModifier {
	private final int roomSize;
	private final boolean generateGround;

	public TowerRoom(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
		super(TFStructurePieceTypes.TOWER_ROOM.get(), compoundTag, ctx, readSettings(compoundTag));

		TowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(TowerUtil.ROOM_SPAWNERS), true);

		this.roomSize = compoundTag.getInt("room_size");
		this.generateGround = compoundTag.getBoolean("gen_ground");
	}

	public TowerRoom(StructureTemplateManager structureManager, int genDepth, JigsawPlaceContext jigsawContext, ResourceLocation roomId, int roomSize, boolean generateGround) {
		super(TFStructurePieceTypes.TOWER_ROOM.get(), genDepth, structureManager, roomId, jigsawContext);

		TowerUtil.addDefaultProcessors(this.placeSettings.addProcessor(TowerUtil.ROOM_SPAWNERS), true);

		this.roomSize = roomSize;
		this.generateGround = generateGround;
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag structureTag) {
		super.addAdditionalSaveData(ctx, structureTag);

		structureTag.putInt("room_size", this.roomSize);
		structureTag.putBoolean("gen_ground", this.generateGround);
	}

	@Override
	protected void processJigsaw(StructurePiece parent, StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord connection, int jigsawIndex) {
		switch (connection.target()) {
			case "twilightforest:lich_tower/bridge" -> {
				boolean generateGround = this.generateGround && connection.pos().getY() < 5;
				if (this.roomSize < 1) {
					return;
				} else if (this.genDepth > 30 || random.nextInt(jigsawIndex + 1) > 1) {
					TowerBridge.putCover(this, pieceAccessor, random, connection.pos(), connection.orientation(), this.structureManager, generateGround, this.genDepth + 1);
				} else {
					TowerBridge.tryRoomAndBridge(this, pieceAccessor, random, connection, this.structureManager, false, this.roomSize - random.nextInt(2) - (random.nextInt(this.genDepth) >> 1), generateGround, this.genDepth + 1, false);
				}
			}
			case "twilightforest:lich_tower/roof" -> {
				FrontAndTop orientationToMatch = getVerticalOrientation(connection, Direction.UP, this);
				BoundingBox roofExtension = BoundingBoxUtils.extrusionFrom(this.boundingBox.minX(), this.boundingBox.maxY() + 1, this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY() + 1, this.boundingBox.maxZ(), orientationToMatch.top().getOpposite(), 1);
				boolean doSideAttachment = pieceAccessor.findCollisionPiece(roofExtension) != null;

				for (ResourceLocation roofLocation : TowerUtil.shuffledRoofs(random, this.roomSize, doSideAttachment)) {
					if (tryRoof(pieceAccessor, random, connection, roofLocation, orientationToMatch, false, this, this.genDepth + 1, this.structureManager)) {
						return;
					}
				}

				ResourceLocation fallbackRoof = TowerUtil.getFallbackRoof(this.roomSize, doSideAttachment);
				tryRoof(pieceAccessor, random, connection, fallbackRoof, orientationToMatch, true, this, this.genDepth + 1, this.structureManager);
			}
			case "twilightforest:lich_tower/beard" -> {
				if (this.generateGround) {
					// Instead of placing a beard structure piece, this piece generates ground with the Beardifier!
					return;
				}

				FrontAndTop orientationToMatch = getVerticalOrientation(connection, Direction.DOWN, this);

				for (ResourceLocation beardLocation : TowerUtil.shuffledBeards(random, this.roomSize)) {
					if (this.tryBeard(pieceAccessor, random, connection, beardLocation, orientationToMatch, false)) {
						return;
					}
				}

				ResourceLocation fallbackBeard = TowerUtil.getFallbackBeard(this.roomSize);
				this.tryBeard(pieceAccessor, random, connection, fallbackBeard, orientationToMatch, true);
			}
		}
	}

	@NotNull
	public static FrontAndTop getVerticalOrientation(JigsawRecord connection, Direction vertical, TwilightJigsawPiece towerRoom) {
		JigsawRecord sourceJigsaw = towerRoom.getSourceJigsaw();
		Direction sourceDirection = JigsawUtil.getAbsoluteHorizontal(sourceJigsaw != null ? sourceJigsaw.orientation() : connection.orientation());

		return FrontAndTop.fromFrontAndTop(vertical, sourceDirection.getOpposite());
	}

	public static boolean tryRoof(StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord connection, @Nullable ResourceLocation roofLocation, FrontAndTop orientationToMatch, boolean allowClipping, TwilightJigsawPiece parent, int newDepth, StructureTemplateManager structureManager) {
		JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(parent, connection.pos(), orientationToMatch, structureManager, roofLocation, "twilightforest:lich_tower/roof", random);

		if (placeableJunction != null) {
			TowerRoof roofPiece = new TowerRoof(newDepth, structureManager, roofLocation, placeableJunction);

			if (allowClipping || pieceAccessor.findCollisionPiece(roofPiece.generationCollisionBox()) == null) {
				pieceAccessor.addPiece(roofPiece);
				roofPiece.addChildren(parent, pieceAccessor, random);

				return true;
			}
		}
		return false;
	}

	private boolean tryBeard(StructurePieceAccessor pieceAccessor, RandomSource random, JigsawRecord connection, @Nullable ResourceLocation beardLocation, FrontAndTop orientationToMatch, boolean allowClipping) {
		JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(this, connection.pos(), orientationToMatch, this.structureManager, beardLocation, "twilightforest:lich_tower/beard", random);

		if (placeableJunction != null) {
			TowerBeard beardPiece = new TowerBeard(this.genDepth + 1, this.structureManager, beardLocation, placeableJunction);

			if (allowClipping || pieceAccessor.findCollisionPiece(beardPiece.generationCollisionBox()) == null) {
				pieceAccessor.addPiece(beardPiece);
				beardPiece.addChildren(this, pieceAccessor, random);

				return true;
			}
		}
		return false;
	}

	@Override
	protected void handleDataMarker(String label, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox boundingBox) {
		String[] splitLabel = label.split(":");
		if (splitLabel.length == 2) {
			Direction direction = DirectionUtil.fromStringOrElse(splitLabel[1], Direction.NORTH);

			switch (splitLabel[0]) {
				case "lectern" -> {
					level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2); // Clears block entity data left by Data Marker

					boolean putMimic = random.nextBoolean();
					BlockState lectern = Blocks.LECTERN.defaultBlockState()
						.setValue(HorizontalDirectionalBlock.FACING, direction)
						.setValue(LecternBlock.HAS_BOOK, !putMimic)
						.rotate(this.placeSettings.getRotation());

					level.setBlock(pos, lectern, 3);

					if (putMimic) {
						DeathTome tomeMimic = TFEntities.DEATH_TOME.value().create(level.getLevel());
						if (tomeMimic != null) {
							tomeMimic.setOnLectern(true);
							tomeMimic.setPersistenceRequired();
							tomeMimic.moveTo(pos, 0, 0);
							tomeMimic.finalizeSpawn(level, level.getCurrentDifficultyAt(tomeMimic.blockPosition()), MobSpawnType.STRUCTURE, null);
							level.addFreshEntityWithPassengers(tomeMimic);
						}
					} else if (level.getBlockEntity(pos) instanceof LecternBlockEntity lecternBlockEntity) {
						lecternBlockEntity.setBook(new ItemStack(Items.WRITABLE_BOOK));
					}
				}
			}
		}
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
		return this.roomSize == 0 ? 2 : 1;
	}
}
