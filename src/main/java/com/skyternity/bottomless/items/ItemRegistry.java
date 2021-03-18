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
    private static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(BottomlessMain.MOD_ID,"group"), () -> new ItemStack(BlockRegistry.MIDSTONE));

    // Items


    // Blockitems
    public static final Item MIDSTONE = register(BlockRegistry.MIDSTONE, "midstone", new Item.Settings().group(GROUP));
    public static final Item SHADESTONE = register(BlockRegistry.SHADESTONE, "shadestone", new Item.Settings().group(GROUP));
    public static final Item CRYSTAL = register(BlockRegistry.CRYSTAL, "crystal", new Item.Settings().group(GROUP));
    public static final Item SMALL_CRYSTAL = register(BlockRegistry.SMALL_CRYSTAL, "small_crystal", new Item.Settings().group(GROUP));
    public static final Item CRYSTAL_PILLAR = register(BlockRegistry.CRYSTAL_PILLAR, "crystal_pillar", new Item.Settings().group(GROUP));
    public static final Item GEYSER = register(BlockRegistry.GEYSER, "geyser", new Item.Settings().group(GROUP));

    // TODO finalize name
    public static final Item TEST_GLOW_BLOCK = register(BlockRegistry.TEST_GLOW_BLOCK, "testglowblock", new Item.Settings().group(GROUP));

    // Methods
    public static void init () {
        // Loads Class(TM)
    }
    private static Item register(Item item, String name) {
        return Registry.register(Registry.ITEM, new Identifier(BottomlessMain.MOD_ID, name), item);
    }

    private static Item register(Block block, String name, Item.Settings settings) {
        return register(new BlockItem(block, settings), name);
    }
}