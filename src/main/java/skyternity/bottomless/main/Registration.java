package skyternity.bottomless.main;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skyternity.bottomless.BottomlessMod;
import skyternity.bottomless.main.block.ModBlocks;
import skyternity.bottomless.main.item.ModItems;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BottomlessMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BottomlessMod.MOD_ID);

    public static void register(){
        IEventBus modeEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modeEventBus);
        ITEMS.register(modeEventBus);

        ModBlocks.register();
        ModItems.register();
    }

}
