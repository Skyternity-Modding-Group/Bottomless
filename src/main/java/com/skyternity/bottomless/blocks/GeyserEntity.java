package com.skyternity.bottomless.blocks;

import com.skyternity.bottomless.BottomlessMain;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class GeyserEntity extends BlockEntity {
    public GeyserEntity(BlockPos pos, BlockState state) {
        super(GEYSER_ENTITY_TYPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GeyserEntity blockEntity) {
        if(world.isClient)
            return;

        Vec3d from = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        Vec3d to = from.add(1D, 2D, 1D);
        Box box = new Box(from, to);
        List<Entity> entities = world.getOtherEntities(null, box);

        for (Entity e : entities) {
            if (!(e instanceof LivingEntity))
                continue;
            LivingEntity entity = (LivingEntity) e;
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 110)); // 5 sec
        }
    }

    public static BlockEntityType<GeyserEntity> GEYSER_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(BottomlessMain.MOD_ID, "geyser"),
            FabricBlockEntityTypeBuilder.create(GeyserEntity::new, BlockRegistry.GEYSER).build()
    );
}
