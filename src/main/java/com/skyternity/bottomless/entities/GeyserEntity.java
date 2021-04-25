package com.skyternity.bottomless.entities;

import com.skyternity.bottomless.blocks.GeyserSource;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class GeyserEntity extends BlockEntity implements BlockEntityClientSerializable {
    public boolean valid;

    public GeyserEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.GEYSER_ENTITY_TYPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GeyserEntity blockEntity) {
        //if(world.isClient)
        //    return;

        // Get source
        GeyserEntity entity = (GeyserEntity) world.getBlockEntity(pos);
        assert entity != null;

        BlockPos sourcePos = pos.down();
        BlockState sourceState = world.getBlockState(sourcePos);
        if(sourceState.getBlock() instanceof GeyserSource) {
            entity.valid = true;

            // Clients end here
            if(world.isClient)
                return;

            entity.sync();

            // Give effect
            Vec3d from = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            Vec3d to = from.add(1D, 2D, 1D);
            Box box = new Box(from, to);
            List<Entity> entities = world.getOtherEntities(null, box);

            for (Entity e : entities) {

                if (!(e instanceof LivingEntity))
                    continue;
                LivingEntity targetEntity = (LivingEntity) e;
                ((GeyserSource) sourceState.getBlock()).applyGeyserEffect(sourceState, world, sourcePos, targetEntity);
            }
        } else {
            entity.valid = false;
        }
    }

    @Override
    public void readNbt(CompoundTag tag) {
        super.readNbt(tag);

        valid = tag.getBoolean("valid");
    }

    @Override
    public CompoundTag writeNbt(CompoundTag tag) {
        super.writeNbt(tag);

        tag.putBoolean("valid", valid);

        return tag;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        valid = tag.getBoolean("valid");
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        super.writeNbt(tag);

        tag.putBoolean("valid", valid);

        return tag;
    }
}
