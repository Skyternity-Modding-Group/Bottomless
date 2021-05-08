package skyternity.bottomless;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import skyternity.bottomless.main.Registration;
import skyternity.bottomless.main.block.ModBlocks;
import skyternity.bottomless.main.item.ModItemGroup;
import software.bernie.geckolib3.GeckoLib;


@Mod(BottomlessMod.MOD_ID)
public class BottomlessMod
{

    public static final String MOD_ID = "bottomless";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final ModItemGroup MAIN_BOTTOMLESS_TAB = new ModItemGroup("bottomless.main", () -> new ItemStack(Items.CARROT));

    public BottomlessMod() {

        GeckoLib.initialize();
        Registration.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ModBlocks.ANCIENT_GLASS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.ANCIENT_GLASS_SHARD.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.SMALL_ANCIENT_GLASS_SHARD.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.LANTERSTALK_DISCFRUITED.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.LANTERSTALK.get(), RenderType.cutoutMipped());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

        }
    }
}
