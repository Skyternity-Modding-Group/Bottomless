package com.skyternity.bottomless.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Geyser extends Block implements BlockEntityProvider {
    public Geyser(FabricBlockSettings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GeyserEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape block1 = cube(8, 0, 1, 15, 10, 8);
        VoxelShape block2 = cube(5, 0, 8, 12, 5, 15);
        VoxelShape block3 = cube(2, 0, 2, 8, 7, 8);

        return VoxelShapes.union(block1, block2, block3);
    }

    private VoxelShape cube(Integer x1, Integer y1, Integer z1, Integer x2, Integer y2, Integer z2) {
        return VoxelShapes.cuboid(
                hexdec(x1), hexdec(y1), hexdec(z1),
                hexdec(x2), hexdec(y2), hexdec(z2)
        );
    }
    private double hexdec(Integer n) {
        return n.doubleValue() / 16D;
    }
}
