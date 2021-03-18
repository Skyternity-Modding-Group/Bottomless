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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class GeyserEntity extends BlockEntity {
    public GeyserEntity(BlockPos pos, BlockState state) {
        super(GEYSER_ENTITY_TYPE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GeyserEntity blockEntity) {
        BottomlessMain.LOGGER.info("e");
        if(world.isClient)
            return;

        List<Entity> entities = world.getOtherEntities(null, Box.from(Vec3d.ofCenter(pos)).expand(0D, 1.5D, 0D));

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
