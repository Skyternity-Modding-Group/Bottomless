package com.skyternity.bottomless.blocks.exp_mushroom;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
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
        Block soil = BlockRegistry.MIDSTONE;
        int stemLength = this.countBlocksBelow(world, pos, BlockRegistry.EXP_SHROOM_STEM);
        int capLenght = this.countBlocksBelow(world, pos, BlockRegistry.EXP_SHROOM_CAP);
        if(stemLength < 2){
            if(world.getBlockState(pos.up()).getBlock() == Blocks.AIR && world.getBlockState(pos.down(stemLength+1)) == soil.getDefaultState()){
                world.setBlockState(pos.up(), BlockRegistry.EXP_SHROOM_CAP_SOURCE.getDefaultState());
                world.setBlockState(pos, BlockRegistry.EXP_SHROOM_STEM.getDefaultState());
                world.updateNeighbors(pos.up(), world.getBlockState(pos.up()).getBlock());
                world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
            }
        }
        if(capLenght < 4 && stemLength <= 2){
            if(world.getBlockState(pos.up()).getBlock() == Blocks.AIR){
                world.setBlockState(pos.up(), BlockRegistry.EXP_SHROOM_CAP_SOURCE.getDefaultState());
                world.setBlockState(pos, BlockRegistry.EXP_SHROOM_CAP.getDefaultState());
                world.updateNeighbors(pos.up(), world.getBlockState(pos.up()).getBlock());
                world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
            }
        }
    }

    public static int countBlocksBelow(BlockView world, BlockPos sourcePos, Block blockToSearchFor) {
        int i;
        for(i = 0; i < 16 && world.getBlockState(sourcePos.down(i + 1)).isOf(blockToSearchFor); ++i) {
        }
        return i;
    }


    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        /**if(world.getBlockState(pos.down()) == BlockRegistry.EXP_SHROOM_STEM.getDefaultState()){
            return true;
        }else if(world.getBlockState(pos.down()).isSolidBlock(world, pos)){
            return true;
        }else{
            return false;
        }**/
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
            world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handItem = player.getStackInHand(hand);
        if(handItem.getItem() == Items.GLASS_BOTTLE.asItem()){
            player.giveItemStack(new ItemStack((Items.EXPERIENCE_BOTTLE)));
            if(!player.isCreative()){
                handItem.decrement(1);
                world.setBlockState(pos, BlockRegistry.EXP_SHROOM_CAP.getDefaultState());
                world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
            }
            return ActionResult.success(world.isClient);
        }else{
            return ActionResult.FAIL;
        }
    }
}
