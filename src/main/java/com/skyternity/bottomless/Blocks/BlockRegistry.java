package com.skyternity.bottomless.Blocks;

import com.skyternity.bottomless.BottomlessMain;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import static net.minecraft.block.Blocks.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    // Bwocks
    public static final Block MIDSTONE = register(new Block(FabricBlockSettings.copyOf(STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES,5)), "midstone");
    public static final Block SHADESTONE = register(new Block(FabricBlockSettings.copyOf(STONE).requiresTool().breakByTool(FabricToolTags.PICKAXES,5)), "shadestone");

    // TODO finalize name
    // TODO change jsons to new name
    // TODO add translation key for new name
    public static final Block TEST_GLOW_BLOCK = register(new GwowwyBwocc(FabricBlockSettings.copy(REDSTONE_ORE)), "testglowblock");

    // Methods
    private static Block register(Block block, String name) {
        return Registry.register(Registry.BLOCK, new Identifier(BottomlessMain.MOD_ID, name), block);
    }

    public static void init() {
        // just loads the class
    }
}
