package skyternity.bottomless.main.block.exp_shroom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import skyternity.bottomless.main.block.ModBlocks;

public class ExpShroomStem extends Block {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public ExpShroomStem(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        if(world.getBlockState(pos.below()) == ModBlocks.EXP_SHROOM_STEM.get().defaultBlockState()){
            return true;
        }else if(world.getBlockState(pos.below()).isSolidRender(world, pos)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos2, boolean notify) {
        if (!canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
            world.neighborChanged(pos, world.getBlockState(pos).getBlock(), pos.above());
        }
    }
}
