package skyternity.bottomless.main.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import skyternity.bottomless.BottomlessMod;
import skyternity.bottomless.main.Registration;
import skyternity.bottomless.main.block.ModBlocks;

public class ModItems {

    public static final RegistryObject<BlockItem> ENCHANTED_GAMMASTONE_BRICKS = Registration.ITEMS.register("enchanted_gammastone_bricks", () -> new EnchGammastoneItem(ModBlocks.ENCHANTED_GAMMASTONE_BRICKS.get(), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB)));

    public static void register(){}

}
