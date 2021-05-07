package com.skyternity.bottomless.blocks.lanterstalk;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LanterStalk extends Block implements Fertilizable {

    public static final EnumProperty<StalkConnectionType> POSITION = EnumProperty.of("stalk_connect_type", StalkConnectionType.class);

    protected static final VoxelShape SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public LanterStalk(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(POSITION, StalkConnectionType.MIDDLE))));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{POSITION});
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if(ctx.getSide() == Direction.DOWN){
            if(ctx.getWorld().getBlockState(ctx.getBlockPos().down()).getBlock().getClass() != LanterStalk.class){
                return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.BOTTOM);
            }else{
                return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.MIDDLE);
            }
        }else if(ctx.getSide() == Direction.UP){
            if(ctx.getWorld().getBlockState(ctx.getBlockPos().up()).getBlock().getClass() != LanterStalk.class){
                return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.TOP);
            }else{
                return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.MIDDLE);
            }
        }
        return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.MIDDLE);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        world.setBlockState(pos, getUpdatedBlockState(world, pos));
        if (!canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
            world.updateNeighbor(pos, world.getBlockState(pos).getBlock(), pos.up());
        }
    }

    public BlockState getUpdatedBlockState(World world ,BlockPos pos){
        if(world.getBlockState(pos.down()).getBlock().getClass() == LanterStalk.class || world.getBlockState(pos.down()).getBlock().getClass() == LanterStalkDiscfruit.class && world.getBlockState(pos.up()).getBlock().getClass() == LanterStalk.class || world.getBlockState(pos.up()).getBlock().getClass() == LanterStalkDiscfruit.class){
            return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.MIDDLE);
        }else if(world.getBlockState(pos.down()).getBlock().getClass() != LanterStalk.class || world.getBlockState(pos.down()).getBlock().getClass() != LanterStalkDiscfruit.class ) {
            return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.TOP);
        }else if(world.getBlockState(pos.up()).getBlock().getClass() != LanterStalk.class || world.getBlockState(pos.up()).getBlock().getClass() != LanterStalkDiscfruit.class) {
            return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.BOTTOM);
        }
        return (BlockState)this.getDefaultState().with(POSITION, StalkConnectionType.MIDDLE);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if(world.getBlockState(pos.down()).getBlock() == BlockRegistry.LANTERSTALK.getDefaultState().getBlock() || world.getBlockState(pos.down()).getBlock() == BlockRegistry.LANTERSTALK_DISCFRUITED.getDefaultState().getBlock()){
            return true;
        }else if(world.getBlockState(pos.up()).getBlock() == BlockRegistry.LANTERSTALK.getDefaultState().getBlock() || world.getBlockState(pos.up()).getBlock() == BlockRegistry.LANTERSTALK_DISCFRUITED.getDefaultState().getBlock()){
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
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState stalkState = world.getBlockState(pos);
        if(stalkState.getBlock() instanceof LanterStalk){
            if(stalkState == this.getDefaultState().with(POSITION, StalkConnectionType.MIDDLE)){
                world.setBlockState(pos, BlockRegistry.LANTERSTALK_DISCFRUITED.getDefaultState());
            }
        }
    }

    public enum StalkConnectionType implements StringIdentifiable {
        TOP("top_ceiling"),
        MIDDLE("middle"),
        BOTTOM("bottom_floor");

        private final String name;

        private StalkConnectionType(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }
    }
}
