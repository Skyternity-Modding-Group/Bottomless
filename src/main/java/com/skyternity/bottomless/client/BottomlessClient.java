package com.skyternity.bottomless.client;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class BottomlessClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerBlockRenderLayers();
    }

    private static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRYSTAL, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SMALL_CRYSTAL, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRYSTAL_PILLAR, RenderLayer.getCutoutMipped());
    }
}
