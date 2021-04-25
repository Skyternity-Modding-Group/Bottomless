package com.skyternity.bottomless.client;

import com.skyternity.bottomless.blocks.BlockRegistry;
import com.skyternity.bottomless.particles.ParticleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class BottomlessClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerBlockRenderLayers();
        ParticleRegistry.init();
    }

    private static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ANCIENT_GLASS_SHARD, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SMALL_ANCIENT_GLASS_SHARD, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ANCIENT_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GEYSER, RenderLayer.getTranslucent());
    }
}
