package com.skyternity.bottomless;

import com.skyternity.bottomless.Blocks.BlockRegistry;
import com.skyternity.bottomless.Items.ItemRegistry;
import com.skyternity.bottomless.WorldGeneration.Dimensions.DimensionRegistry;
import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.init;

public class BottomlessMain implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "bottomless";
    public static final String MOD_NAME = "Bottomless";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        //TODO: Initializer
        ItemRegistry.init();
        BlockRegistry.init();
        DimensionRegistry.init();
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}