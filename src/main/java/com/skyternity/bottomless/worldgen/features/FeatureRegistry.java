package com.skyternity.bottomless.worldgen.features;

import com.skyternity.bottomless.BottomlessMain;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class FeatureRegistry {
    // Bottomless Pillar
    public static final Feature<DefaultFeatureConfig> PILLAR_FEATURE =
            Registry.register(Registry.FEATURE, new Identifier(BottomlessMain.MOD_ID, "pillar_feature"), new PillarFeature(DefaultFeatureConfig.CODEC));
    public static final ConfiguredFeature<?, ?> PILLAR_CONFIGURED = PILLAR_FEATURE.configure(FeatureConfig.DEFAULT)
            .decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(50)));
    public static final RegistryKey<ConfiguredFeature<?, ?>> PILLAR_KEY = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
            new Identifier(BottomlessMain.MOD_ID, "pillar_feature"));

    public static void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, PILLAR_KEY.getValue(), PILLAR_CONFIGURED);
    }
}
