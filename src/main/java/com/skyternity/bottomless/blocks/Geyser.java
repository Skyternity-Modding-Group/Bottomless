package com.skyternity.bottomless.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Geyser extends BlockWithEntity {
    public Geyser(FabricBlockSettings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GeyserEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, GeyserEntity.GEYSER_ENTITY_TYPE, GeyserEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Taken from models/block/geyser.json
        VoxelShape block1 = BlockUtil.cube(8, 0, 1, 15, 10, 8);
        VoxelShape block2 = BlockUtil.cube(5, 0, 8, 12, 5, 15);
        VoxelShape block3 = BlockUtil.cube(2, 0, 2, 8, 7, 8);

        return VoxelShapes.union(block1, block2, block3);
    }
}
