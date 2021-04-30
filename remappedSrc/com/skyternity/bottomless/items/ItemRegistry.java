package com.skyternity.bottomless.items;

import com.skyternity.bottomless.blocks.BlockRegistry;
import com.skyternity.bottomless.BottomlessMain;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(BottomlessMain.MOD_ID,"group"), () -> new ItemStack(BlockRegistry.MIDSTONE));

    // Items

    // BlockItems
    public static final Item ENCHANTED_GAMMASTONE_BRICKS = register(new EnchGammastoneItem(BlockRegistry.ENCHANTED_GAMMASTONE_BRICKS, new Item.Settings().group(GROUP)), "enchanted_gammastone_bricks");


    // Methods
    public static void init () {
        // Loads Class(TM)
    }

    private static Item register(Item item, String name) {
        return Registry.register(Registry.ITEM, new Identifier(BottomlessMain.MOD_ID, name), item);
    }

    public static Item registerBlockItem(Block block, String name, Item.Settings settings) {
        return register(new BlockItem(block, settings), name);
    }
}