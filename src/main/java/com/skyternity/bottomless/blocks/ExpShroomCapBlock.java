package com.skyternity.bottomless.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class ExpShroomCapBlock extends Block implements Fertilizable {

    public ExpShroomCapBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) { return true; }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        grow(world, random, pos, state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int stemLength = this.countStemLength(world, pos);
        if(stemLength <= 5){ //its a bug or glitch or whatever is that, but that 5 is actually 7. so like, a+2; keep that in mind.
            if(world.getBlockState(pos.up()).getBlock() == Blocks.AIR && world.getBlockState(pos.down(stemLength+1)) == BlockRegistry.MIDSTONE.getDefaultState()){
                world.setBlockState(pos.up(), BlockRegistry.EXP_SHROOM_CAP.getDefaultState());
                world.setBlockState(pos, BlockRegistry.EXP_SHROOM_STEM.getDefaultState());
                world.updateNeighbors(pos.up(), world.getBlockState(pos.up()).getBlock());
                world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
            }
        }else{
            //cant grow more
        }
    }

    protected int countStemLength(BlockView world, BlockPos pos) {
        int i;
        for(i = 0; i < 16 && world.getBlockState(pos.down(i + 1)).isOf(BlockRegistry.EXP_SHROOM_STEM); ++i) {
        }
        return i;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(world.getBlockState(pos.down()) == BlockRegistry.EXP_SHROOM_STEM.getDefaultState()){
            return true;
        }else if(world.getBlockState(pos.down()).isSolidBlock(world, pos)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
            world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
        }
    }

}
