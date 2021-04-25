package com.skyternity.bottomless.entities;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.blocks.BlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    // BlockEntityTypes
    public static BlockEntityType<GeyserEntity> GEYSER_ENTITY_TYPE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(BottomlessMain.MOD_ID, "geyser"),
            FabricBlockEntityTypeBuilder.create(GeyserEntity::new, BlockRegistry.GEYSER).build()
    );
    public static BlockEntityType<PorousShadestoneEntity> POROUS_SHADESTONE_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(BottomlessMain.MOD_ID, "porous_shadestone"),
            FabricBlockEntityTypeBuilder.create(PorousShadestoneEntity::new, BlockRegistry.POROUS_SHADESTONE).build()
    );

    public static void init() {}
}
