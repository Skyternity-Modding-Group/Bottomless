package com.skyternity.bottomless.blocks;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.items.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import static net.minecraft.block.Blocks.*;

import net.minecraft.block.FallingBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    // Bwocks
    public static final Block MIDSTONE = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES,5)), "midstone", ItemRegistry.GROUP);
    public static final Block SHADESTONE = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES,5)), "shadestone", ItemRegistry.GROUP);
    public static final Block ANCIENT_GLASS_SHARD = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(GLASS).noCollision()), "ancient_glass_shard", ItemRegistry.GROUP);
    public static final Block SMALL_ANCIENT_GLASS_SHARD = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(GLASS).noCollision()), "small_ancient_glass_shard", ItemRegistry.GROUP);
    public static final Block ANCIENT_GLASS = registerBlockWithItem(new CrystalBlock(FabricBlockSettings.copyOf(GLASS)), "ancient_glass", ItemRegistry.GROUP);

    public static final Block BLACK_SAND = registerBlockWithItem(new FallingBlock(FabricBlockSettings.copyOf(SAND).breakByHand(true)), "black_sand", ItemRegistry.GROUP);

    // TODO finalize name
    // TODO change jsons to new name
    // TODO add translation key for new name
    public static final Block TEST_GLOW_BLOCK = registerBlockWithItem(new GwowwyBwocc(FabricBlockSettings.copy(REDSTONE_ORE)), "testglowblock", ItemRegistry.GROUP);

    // Methods
    private static Block register(Block block, String name) {
        return Registry.register(Registry.BLOCK, new Identifier(BottomlessMain.MOD_ID, name), block);
    }

    private static Block registerBlockWithItem(Block block, String name, ItemGroup group){ //the item group can be switched to demand ItemSettings instead of an ItemGroup, so each setting can be specified when calling the method.
        Block regObj = register(block, name);
        ItemRegistry.registerBlockItem(block, name, new Item.Settings().group(group));
        return regObj;
    }

    public static void init() {
        // just loads the class
    }
}
