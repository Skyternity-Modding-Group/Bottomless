package com.skyternity.bottomless.blocks.lanterstalk;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class LanterStalkDiscfruit extends Block {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public LanterStalkDiscfruit(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(world.getBlockState(pos.down()).getBlock() == BlockRegistry.LANTERSTALK.getDefaultState().getBlock()){
            return true;
        }else if(world.getBlockState(pos.up()).getBlock() == BlockRegistry.LANTERSTALK.getDefaultState().getBlock()){
            return true;
        }else if(world.getBlockState(pos.down()).isSolidBlock(world, pos)){
            return true;
        }else if(world.getBlockState(pos.up()).isSolidBlock(world, pos)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
            world.updateNeighbor(pos, world.getBlockState(pos).getBlock(), pos.up());
        }
    }

}
