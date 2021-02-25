package com.skyternity.bottomless.WorldGeneration.Features;

import com.skyternity.bottomless.Blocks.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.Mixins.GenerationSettingsAccessor;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class FeaturesMain {
    public static final ConfiguredFeature<?, ?> MIDSTONE = register("midstone", Feature.ORE.configure(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.BEDROCK), BlockRegistry.MIDSTONE.getDefaultState(), 8)).repeat(6));;

    public static ConfiguredFeature<?, ?> register(String name, ConfiguredFeature<?, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(BottomlessMain.MOD_ID, name), feature);
    }

    public static void init() {
        BuiltinRegistries.BIOME.stream()
                .filter(biome -> biome.getCategory().equals(Biome.Category.PLAINS))
                .forEach(biome -> {
                    final List<List<Supplier<ConfiguredFeature<?, ?>>>> features  = new ArrayList<>(biome.getGenerationSettings().getFeatures());
                    List<Supplier<ConfiguredFeature<?, ?>>> ores;
                    try {
                        ores = new ArrayList<>(features.get(GenerationStep.Feature.UNDERGROUND_ORES.ordinal()));
                    } catch (RuntimeException e) {
                        ores = new ArrayList<>();
                    }
                    try {
                        features.set(GenerationStep.Feature.UNDERGROUND_ORES.ordinal(), ores);
                    } catch (RuntimeException e) {
                        while (features.size() < GenerationStep.Feature.UNDERGROUND_ORES.ordinal()) {
                            features.add(ImmutableList.of());
                        }
                        features.add(GenerationStep.Feature.UNDERGROUND_ORES.ordinal(), ores);
                    }
                    ores.add(() -> MIDSTONE);
                    ((GenerationSettingsAccessor) biome.getGenerationSettings()).setFeatures(features);
                });
    }
}