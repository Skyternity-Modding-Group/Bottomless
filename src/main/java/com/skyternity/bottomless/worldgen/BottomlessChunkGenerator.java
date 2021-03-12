package com.skyternity.bottomless.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skyternity.bottomless.BottomlessMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BottomlessChunkGenerator extends ChunkGenerator {
    public static final Codec<BottomlessChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                BiomeSource.CODEC.fieldOf("biome_source")
                    .forGetter((generator) -> generator.biomeSource),
                StructuresConfig.CODEC.fieldOf("structures").forGetter(ChunkGenerator::getStructuresConfig)
            )
            .apply(instance, instance.stable(BottomlessChunkGenerator::new))
    );

    private final ChunkRandom random;
    private final SimplexNoiseSampler islandNoise;
    private final StructuresConfig structureConfig;
    private final int height;

    public BottomlessChunkGenerator(BiomeSource biomeSource, StructuresConfig structureConfig) {
        this(biomeSource, biomeSource, structureConfig);
    }

    private BottomlessChunkGenerator(BiomeSource biomeSource, BiomeSource biomeSource2, StructuresConfig structureConfig) {
        super(biomeSource, biomeSource2, structureConfig, 0);

        this.random = new ChunkRandom(0);

        islandNoise = new SimplexNoiseSampler(random);
        this.structureConfig = structureConfig;
        this.height = 256;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ChunkGenerator withSeed(long seed) {
        return new BottomlessChunkGenerator(this.biomeSource.withSeed(seed), this.structureConfig);
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {

    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return this.height;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return new VerticalBlockSample(this.height, new BlockState[0]);
    }
}
