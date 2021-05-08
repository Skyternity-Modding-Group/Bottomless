package skyternity.bottomless.main.block.exp_shroom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import skyternity.bottomless.main.block.ModBlocks;

import java.util.Random;

public class ExpShroomCap extends Block implements IGrowable {

    public ExpShroomCap(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        performBonemeal(world, random, pos, state);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) { return true; }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean p_176473_4_) { return true; }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState state) { return true; }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Block soil = ModBlocks.MIDSTONE.get();
        int stemLength = this.countBlocksBelow(world, pos, ModBlocks.EXP_SHROOM_STEM.get());
        int capLenght = this.countBlocksBelow(world, pos, ModBlocks.EXP_SHROOM_CAP.get());
        if(stemLength < 2){
            if(world.getBlockState(pos.above()).getBlock() == Blocks.AIR && world.getBlockState(pos.below(stemLength+1)) == soil.defaultBlockState()){
                world.setBlockAndUpdate(pos.above(), ModBlocks.EXP_SHROOM_CAP_SOURCE.get().defaultBlockState());
                world.setBlockAndUpdate(pos, ModBlocks.EXP_SHROOM_STEM.get().defaultBlockState());
                world.updateNeighborsAt(pos.above(), world.getBlockState(pos.above()).getBlock());
                world.updateNeighborsAt(pos, world.getBlockState(pos).getBlock());
            }
        }
        if(capLenght < 4 && stemLength <= 2){
            if(world.getBlockState(pos.above()).getBlock() == Blocks.AIR){
                world.setBlockAndUpdate(pos.above(), ModBlocks.EXP_SHROOM_CAP_SOURCE.get().defaultBlockState());
                world.setBlockAndUpdate(pos, ModBlocks.EXP_SHROOM_CAP.get().defaultBlockState());
                world.updateNeighborsAt(pos.above(), world.getBlockState(pos.above()).getBlock());
                world.updateNeighborsAt(pos, world.getBlockState(pos).getBlock());
            }
        }
    }

    public static int countBlocksBelow(IBlockReader world, BlockPos sourcePos, Block blockToSearchFor) {
        int i;
        for(i = 0; i < 16 && world.getBlockState(sourcePos.below(i + 1)).is(blockToSearchFor); ++i) {
        }
        return i;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack handItem = player.getItemInHand(hand);
        if(handItem.getItem() == Items.GLASS_BOTTLE.asItem()){
            player.addItem(new ItemStack((Items.EXPERIENCE_BOTTLE)));
            if(!player.isCreative()){
                handItem.shrink(1);
                world.setBlockAndUpdate(pos, ModBlocks.EXP_SHROOM_CAP.get().defaultBlockState());
                world.updateNeighborsAt(pos, world.getBlockState(pos).getBlock());
            }
            return ActionResultType.CONSUME;
        }else{
            return ActionResultType.FAIL;
        }
    }
}
