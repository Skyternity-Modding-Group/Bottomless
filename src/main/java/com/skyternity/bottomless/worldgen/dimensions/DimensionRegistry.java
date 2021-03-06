package com.skyternity.bottomless.worldgen.dimensions;

import com.mojang.serialization.Codec;
import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.worldgen.BottomlessChunkGenerator;
import com.skyternity.bottomless.worldgen.BottomlessSurfaceBuilder;
import com.skyternity.bottomless.worldgen.BottomlessSurfaceBuilderConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class DimensionRegistry {
    public static final RegistryKey<World> BOTTOMLESS_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(BottomlessMain.MOD_ID, "the_void"));
    public static final RegistryKey<DimensionType> TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(BottomlessMain.MOD_ID, "the_void"));

    public static final SurfaceBuilder<BottomlessSurfaceBuilderConfig> BOTTOMLESS_SURFACE_BUILDER =
            Registry.register(Registry.SURFACE_BUILDER, new Identifier(BottomlessMain.MOD_ID, "surface_builder"), new BottomlessSurfaceBuilder());

    public static final Codec<BottomlessChunkGenerator> BOTTOMLESS_CHUNK_GENERATOR =
            Registry.register(Registry.CHUNK_GENERATOR, new Identifier(BottomlessMain.MOD_ID, "chunk_generator"), BottomlessChunkGenerator.CODEC);

    public static void init() {

    }
}
