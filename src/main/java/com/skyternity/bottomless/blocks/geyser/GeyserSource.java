package com.skyternity.bottomless.blocks.geyser;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface GeyserSource {
    <T extends LivingEntity> void applyGeyserEffect(BlockState state, World world, BlockPos pos, T entity);
}
