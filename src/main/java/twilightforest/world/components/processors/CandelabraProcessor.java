package twilightforest.world.components.processors;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.LightableBlock;
import twilightforest.init.TFBlocks;

@Deprecated // Band-aid processor
public class CandelabraProcessor extends StructureProcessor {
	public static final CandelabraProcessor INSTANCE = new CandelabraProcessor(false);
	public static final CandelabraProcessor INSTANCE_DIM = new CandelabraProcessor(true);
	public static final MapCodec<CandelabraProcessor> CODEC = MapCodec.unit(INSTANCE_DIM);

	private final LightableBlock.Lighting lighting;

	private CandelabraProcessor(boolean dim) {
		this.lighting = dim ? LightableBlock.Lighting.DIM : LightableBlock.Lighting.NORMAL;
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos piecePos, StructureTemplate.StructureBlockInfo originalInfo, StructureTemplate.StructureBlockInfo modifiedInfo, StructurePlaceSettings placeSettings, @Nullable StructureTemplate template) {
		if (modifiedInfo.state().is(TFBlocks.CANDELABRA)) {
			BlockState newState = modifiedInfo.state().setValue(CandelabraBlock.LIGHTING, this.lighting);

			// FIXME Fix blockstate <-> blockentity parity for candles, for data resiliency
			for (BooleanProperty prop : CandelabraBlock.CANDLES) {
				newState = newState.setValue(prop, true);
			}

			return new StructureTemplate.StructureBlockInfo(modifiedInfo.pos(), newState, modifiedInfo.nbt());
		}

		return modifiedInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return null;
	}
}
