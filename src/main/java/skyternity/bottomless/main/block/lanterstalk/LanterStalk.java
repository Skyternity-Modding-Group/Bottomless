package skyternity.bottomless.main.block.lanterstalk;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import skyternity.bottomless.main.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class LanterStalk extends Block implements IGrowable {

    public static final EnumProperty<StalkConnectionType> POSITION = EnumProperty.create("stalk_connect_type", StalkConnectionType.class);

    protected static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public LanterStalk(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POSITION, StalkConnectionType.MIDDLE));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POSITION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return getUpdatedBlockState(ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos2, boolean notify) {
        world.setBlockAndUpdate(pos, getUpdatedBlockState(world, pos));
        if (!canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
            world.neighborChanged(pos, world.getBlockState(pos).getBlock(), pos.above());
        }
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        Block blockBelow = world.getBlockState(pos.below()).getBlock();
        Block blockAbove = world.getBlockState(pos.above()).getBlock();
        if(blockBelow == ModBlocks.LANTERSTALK.get() || blockBelow == ModBlocks.LANTERSTALK_DISCFRUITED.get()){
            return true;
        }else if(blockAbove == ModBlocks.LANTERSTALK.get() || blockAbove == ModBlocks.LANTERSTALK_DISCFRUITED.get()){
            return true;
        }else if(world.getBlockState(pos.below()).isSolidRender(world, pos)){
            return true;
        }else if(world.getBlockState(pos.above()).isSolidRender(world, pos)){
            return true;
        }else{
            return false;
        }
    }

    public BlockState getUpdatedBlockState(World world , BlockPos pos){
        Block blockBelow = world.getBlockState(pos.below()).getBlock();
        Block blockAbove = world.getBlockState(pos.above()).getBlock();

        if(blockBelow == ModBlocks.LANTERSTALK.get() || blockBelow == ModBlocks.LANTERSTALK_DISCFRUITED.get()){
            if(blockAbove == ModBlocks.LANTERSTALK.get() || blockAbove == ModBlocks.LANTERSTALK_DISCFRUITED.get()){
                return (BlockState)this.defaultBlockState().setValue(POSITION, StalkConnectionType.MIDDLE);
            }else{
                if(blockAbove == Blocks.AIR){
                    return (BlockState)this.defaultBlockState().setValue(POSITION, StalkConnectionType.MIDDLE);
                }else{
                    return (BlockState)this.defaultBlockState().setValue(POSITION, StalkConnectionType.BOTTOM);
                }
            }
        }else{
            if(blockBelow == Blocks.AIR){
                return (BlockState)this.defaultBlockState().setValue(POSITION, StalkConnectionType.MIDDLE);
            }else{
                return (BlockState)this.defaultBlockState().setValue(POSITION, StalkConnectionType.TOP);
            }
        }

    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_) { return true; }

    @Override
    public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) { return true; }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        performBonemeal(world, random, pos, state);
    }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState stalkState = world.getBlockState(pos);
        BlockState stalkStateAbove = world.getBlockState(pos.above());
        BlockState stalkStateBelow = world.getBlockState(pos.below());

        if(stalkState == this.defaultBlockState().setValue(POSITION, StalkConnectionType.MIDDLE)){
            if(stalkStateAbove.getBlock() == Blocks.AIR || stalkStateBelow.getBlock() == Blocks.AIR){
                if(stalkStateAbove.getBlock() == Blocks.AIR){
                    world.setBlockAndUpdate(pos.above(), getUpdatedBlockState(world, pos.above()));
                }else if(stalkStateBelow.getBlock() == Blocks.AIR){
                    world.setBlockAndUpdate(pos.below(), getUpdatedBlockState(world, pos.below()));
                }
            }else{
                world.setBlockAndUpdate(pos, ModBlocks.LANTERSTALK_DISCFRUITED.get().defaultBlockState());
            }

        }else{
            if(stalkState == this.defaultBlockState().setValue(POSITION, StalkConnectionType.BOTTOM)){
                if(stalkStateBelow.getBlock() == Blocks.AIR){
                    world.setBlockAndUpdate(pos.below(), getUpdatedBlockState(world, pos.below()));
                }
            }else if(stalkState == this.defaultBlockState().setValue(POSITION, StalkConnectionType.TOP)){
                if(stalkStateAbove.getBlock() == Blocks.AIR){
                    world.setBlockAndUpdate(pos.above(), getUpdatedBlockState(world, pos.above()));
                }
            }
        }
    }

    public enum StalkConnectionType implements IStringSerializable {
        TOP("top_ceiling"),
        MIDDLE("middle"),
        BOTTOM("bottom_floor");

        private final String name;

        private StalkConnectionType(String name) {
            this.name = name;
        }

        public String getSerializedName() {
            return this.name;
        }

    }
}
