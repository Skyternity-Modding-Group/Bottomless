package com.skyternity.bottomless.blocks.geyser;

import com.skyternity.bottomless.blocks.BlockUtil;
import com.skyternity.bottomless.entities.EntityRegistry;
import com.skyternity.bottomless.entities.GeyserEntity;
import com.skyternity.bottomless.particles.ParticleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

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
        return checkType(type, EntityRegistry.GEYSER_ENTITY_TYPE, GeyserEntity::tick);
    }

    // Rendering

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        GeyserEntity entity = (GeyserEntity) world.getBlockEntity(pos);
        assert entity != null;
        if(entity.valid) {
            spawnParticles(world, pos);
        }
    }
    @Environment(EnvType.CLIENT)
    private void spawnParticles(World world, BlockPos pos) {
        if(!world.getBlockState(pos).isOpaqueFullCube(world, pos)) {
            Vec3d pos1 = BlockUtil.offset(pos, 12D, 10D, 5D);
            particleSpout(world, pos1, pos);

            Vec3d pos2 = BlockUtil.offset(pos, 8D, 5D, 11D);
            particleSpout(world, pos2, pos);

            Vec3d pos3 = BlockUtil.offset(pos, 5, 7, 5);
            particleSpout(world, pos3, pos);
        }
    }
    private void particleSpout(World world, Vec3d pos, BlockPos blockPos) {
        world.addParticle(ParticleRegistry.GEYSER_PARTICLE, pos.x, pos.y, pos.z, blockPos.getX(), blockPos.getY(), blockPos.getZ());
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
