package com.skyternity.bottomless.client;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.blocks.BlockRegistry;
import com.skyternity.bottomless.particles.ParticleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class BottomlessClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerBlockRenderLayers();
        ParticleRegistry.init();
    }

    private static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRYSTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SMALL_CRYSTAL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRYSTAL_PILLAR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GEYSER, RenderLayer.getTranslucent());
    }
}
