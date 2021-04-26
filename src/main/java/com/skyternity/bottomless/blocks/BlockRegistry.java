package com.skyternity.bottomless.blocks;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.items.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import static net.minecraft.block.Blocks.*;

import net.minecraft.block.FallingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    // Bwocks
    public static final Block MIDSTONE = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES,4)), "midstone", ItemRegistry.GROUP);
    public static final Block SHADESTONE = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES,4)), "shadestone", ItemRegistry.GROUP);
    public static final Block ANCIENT_GLASS_SHARD = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(GLASS).noCollision()), "ancient_glass_shard", ItemRegistry.GROUP);
    public static final Block SMALL_ANCIENT_GLASS_SHARD = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(GLASS).noCollision()), "small_ancient_glass_shard", ItemRegistry.GROUP);
    public static final Block ANCIENT_GLASS = registerBlockWithItem(new CrystalBlock(FabricBlockSettings.copyOf(GLASS)), "ancient_glass", ItemRegistry.GROUP);
    public static final Block BLACK_SAND = registerBlockWithItem(new FallingBlock(FabricBlockSettings.copyOf(SAND).breakByTool(FabricToolTags.SHOVELS)), "black_sand", ItemRegistry.GROUP);
    public static final Block EXP_SHROOM_STEM = registerBlockWithItem(new ExpShroomStemBlock(FabricBlockSettings.copyOf(BAMBOO).breakByTool(FabricToolTags.AXES)), "experience_mushroom_stem", ItemRegistry.GROUP);
    public static final Block EXP_SHROOM_CAP = registerBlockWithItem(new Block(FabricBlockSettings.copyOf(RED_MUSHROOM_BLOCK).mapColor(MapColor.DARK_AQUA)), "experience_mushroom_cap", ItemRegistry.GROUP);
    public static final Block EXP_SHROOM_CAP_SOURCE = registerBlockWithItem(new ExpShroomCapBlock(FabricBlockSettings.copyOf(RED_MUSHROOM_BLOCK).mapColor(MapColor.TEAL).breakByTool(FabricToolTags.AXES).ticksRandomly()), "experience_mushroom_cap_source", ItemRegistry.GROUP);
    public static final Block GEYSER = registerBlockWithItem(new Geyser(FabricBlockSettings.copyOf(STONE).breakByTool(FabricToolTags.PICKAXES, 5).nonOpaque()), "geyser", ItemRegistry.GROUP);
    public static final Block POROUS_SHADESTONE = registerBlockWithItem(new PorousShadestone(FabricBlockSettings.copyOf(STONE).breakByTool(FabricToolTags.PICKAXES, 5)), "porous_shadestone", ItemRegistry.GROUP);

    // TODO finalize name
    // TODO change jsons to new name
    // TODO add translation key for new nameasdasdasdawasd
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
