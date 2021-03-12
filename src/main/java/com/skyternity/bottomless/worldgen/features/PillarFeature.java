package com.skyternity.bottomless.worldgen.features;

import com.mojang.serialization.Codec;
import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class PillarFeature extends Feature<DefaultFeatureConfig> {
    public PillarFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos pos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, context.getOrigin());

        int r = random.nextInt(15) + 5; // pillar's radius
        int h = random.nextInt(50) + 100; // pillar's height
        int b = random.nextInt(100) + 100; // pillar's distance from the void

        for(int i = 0; i <= h; i++) {
            for(int j = MathHelper.floor(-r); j <= MathHelper.ceil(r); j++) {
                for(int k = MathHelper.floor(-r); k <= MathHelper.ceil(r); k++) {
                    if((float)(j * j + k * k) <= (float)(r * r))
                        this.setBlockState(world, pos.add(j, i + b, k), BlockRegistry.SHADESTONE.getDefaultState());
                }
            }
        }

        return true;
    }
}
