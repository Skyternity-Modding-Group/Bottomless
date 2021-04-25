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

    /** // Blockitems
    public static final Item MIDSTONE = registerBlockItem(BlockRegistry.MIDSTONE, "midstone", new Item.Settings().group(GROUP));
    public static final Item SHADESTONE = registerBlockItem(BlockRegistry.SHADESTONE, "shadestone", new Item.Settings().group(GROUP));
    public static final Item CRYSTAL = registerBlockItem(BlockRegistry.ANCIENT_GLASS_SHARD, "ancient_glass_shard", new Item.Settings().group(GROUP));
    public static final Item SMALL_CRYSTAL = registerBlockItem(BlockRegistry.SMALL_ANCIENT_GLASS_SHARD, "small_ancient_glass_shard", new Item.Settings().group(GROUP));
    public static final Item CRYSTAL_PILLAR = registerBlockItem(BlockRegistry.ANCIENT_GLASS, "ancient_glass", new Item.Settings().group(GROUP));
    public static final Item GEYSER = register(BlockRegistry.GEYSER, "geyser", new Item.Settings().group(GROUP));
    public static final Item POROUS_SHADESTONE = register(BlockRegistry.POROUS_SHADESTONE, "porous_shadestone", new Item.Settings().group(GROUP));

    // TODO finalize name
    public static final Item TEST_GLOW_BLOCK = registerBlockItem(BlockRegistry.TEST_GLOW_BLOCK, "testglowblock", new Item.Settings().group(GROUP));

     Who the hell registers block items by hand? Who was your lazyness teacher, huh? i wanna speak with them, NOW!
     -Laz

    **/

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