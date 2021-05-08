package skyternity.bottomless.main.block.ancient_glass;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AncientGlassBlock extends RotatedPillarBlock {
    public AncientGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return super.propagatesSkylightDown(p_200123_1_, p_200123_2_, p_200123_3_);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean skipRendering(BlockState state, BlockState state2, Direction direction) {
        return state2.is(this) ? true : super.skipRendering(state, state2, direction);
    }
}
