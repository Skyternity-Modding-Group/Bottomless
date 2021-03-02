package com.skyternity.bottomless.WorldGeneration.Dimensions;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.WorldGeneration.BottomlessSurfaceBuilder;
import com.skyternity.bottomless.WorldGeneration.BottomlessSurfaceBuilderConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class DimensionRegistry {
    public static final RegistryKey<World> BOTTOMLESS_WORLD_KEY = RegistryKey.of(Registry.DIMENSION, new Identifier(BottomlessMain.MOD_ID, "the_void"));
    public static final RegistryKey<DimensionType> TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, new Identifier(BottomlessMain.MOD_ID, "the_void"));

    public static final SurfaceBuilder<BottomlessSurfaceBuilderConfig> BOTTOMLESS_SURFACE_BUILDER =
            Registry.register(Registry.SURFACE_BUILDER, new Identifier(BottomlessMain.MOD_ID, "surface_builder"), new BottomlessSurfaceBuilder());

    public static void init() {

    }
}
