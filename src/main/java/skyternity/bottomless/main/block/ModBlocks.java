package skyternity.bottomless.main.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import skyternity.bottomless.BottomlessMod;
import skyternity.bottomless.main.Registration;
import skyternity.bottomless.main.block.ancient_glass.AncientGlassBlock;
import skyternity.bottomless.main.block.ancient_glass.AncientGlassShard;
import skyternity.bottomless.main.block.exp_shroom.ExpShroomCap;
import skyternity.bottomless.main.block.exp_shroom.ExpShroomStem;
import skyternity.bottomless.main.block.gammastone.EnchGammastoneBricksBlock;
import skyternity.bottomless.main.block.gammastone.EnchGammastoneTileEntity;
import skyternity.bottomless.main.block.gammastone.GammastoneBricksBlock;
import skyternity.bottomless.main.block.geyser.GeyserBlock;
import skyternity.bottomless.main.block.geyser.GeyserTileEntity;
import skyternity.bottomless.main.block.lanterstalk.LanterStalk;
import skyternity.bottomless.main.block.lanterstalk.LanterStalkDiscfruit;
import skyternity.bottomless.main.block.porus_shadestone.PorusShadestoneBlock;
import skyternity.bottomless.main.block.porus_shadestone.PorusShadestoneTileEntity;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<Block> MIDSTONE = registerWithItem("midstone", () -> new Block(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(50.0f, 1200.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> SHADESTONE = registerWithItem("shadestone", () -> new Block(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(50.0f, 1200.0f).harvestTool(ToolType.PICKAXE).harvestLevel(4)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> ANCIENT_GLASS = registerWithItem("ancient_glass", () -> new AncientGlassBlock(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().sound(SoundType.GLASS).strength(0.3f, 0.3f).isViewBlocking(ModBlocks::never)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> ANCIENT_GLASS_SHARD = registerWithItem("ancient_glass_shard", () -> new AncientGlassShard(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).noCollission().strength(0.3f, 0.3f)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> SMALL_ANCIENT_GLASS_SHARD = registerWithItem("small_ancient_glass_shard", () -> new AncientGlassShard(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).noCollission().strength(0.3f, 0.3f)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> BLACK_SAND = registerWithItem("black_sand", () -> new Block(AbstractBlock.Properties.of(Material.SAND).sound(SoundType.SAND).strength(0.5f, 0.5f).harvestTool(ToolType.SHOVEL)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> EXP_SHROOM_CAP_SOURCE = registerWithItem("experience_mushroom_cap_source", () -> new ExpShroomCap(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.FUNGUS).strength(0.5f, 0.5f).harvestTool(ToolType.HOE)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> EXP_SHROOM_CAP = registerWithItem("experience_mushroom_cap", () -> new Block(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.FUNGUS).strength(0.5f, 0.5f).harvestTool(ToolType.HOE)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> EXP_SHROOM_STEM = registerWithItem("experience_mushroom_stem", () -> new ExpShroomStem(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.5f, 0.5f).harvestTool(ToolType.HOE)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> POROUS_SHADESTONE = registerWithItem("porous_shadestone", () -> new PorusShadestoneBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(50.0f, 1200.0f).harvestTool(ToolType.PICKAXE).harvestLevel(4)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<TileEntityType<PorusShadestoneTileEntity>> PORUS_SHADESTONE_TILE = Registration.TILE_ENTITY_TYPES.register("porous_shadestone", () -> TileEntityType.Builder.of(PorusShadestoneTileEntity::new, POROUS_SHADESTONE.get()).build(null));
    public static final RegistryObject<Block> GAMMASTONE_BRICKS = registerWithItem("gammastone_bricks", () -> new GammastoneBricksBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0f, 12).harvestTool(ToolType.PICKAXE).harvestLevel(4)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> ENCHANTED_GAMMASTONE_BRICKS = registerNoItem("enchanted_gammastone_bricks", () -> new EnchGammastoneBricksBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0f, 12).harvestTool(ToolType.PICKAXE).harvestLevel(4)));
    public static final RegistryObject<TileEntityType<EnchGammastoneTileEntity>> ENCHANTED_GAMMASTONE_BRICKS_TILE = Registration.TILE_ENTITY_TYPES.register("enchanted_gammastone_bricks", () -> TileEntityType.Builder.of(EnchGammastoneTileEntity::new, ENCHANTED_GAMMASTONE_BRICKS.get()).build(null));
    public static final RegistryObject<Block> LANTERSTALK = registerWithItem("lanterstalk", () -> new LanterStalk(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.0f, 0.0f).harvestTool(ToolType.HOE)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> LANTERSTALK_DISCFRUITED = registerWithItem("lanterstalk_discfruit", () -> new LanterStalkDiscfruit(AbstractBlock.Properties.of(Material.BAMBOO).sound(SoundType.BAMBOO).strength(0.0f, 0.0f).harvestTool(ToolType.HOE)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<Block> GEYSER = registerWithItem("geyser", () -> new GeyserBlock(AbstractBlock.Properties.of(Material.STONE).sound(SoundType.STONE).strength(50.0f, 1200.0f).harvestTool(ToolType.PICKAXE).harvestLevel(4)), new Item.Properties().tab(BottomlessMod.MAIN_BOTTOMLESS_TAB));
    public static final RegistryObject<TileEntityType<GeyserTileEntity>> GEYSER_TILE = Registration.TILE_ENTITY_TYPES.register("geyser", () -> TileEntityType.Builder.of(GeyserTileEntity::new, GEYSER.get()).build(null));

    //public static final Block GEYSER = registerBlockWithItem(new Geyser(FabricBlockSettings.copyOf(STONE).breakByTool(FabricToolTags.PICKAXES, 5).nonOpaque()), "geyser", ItemRegistry.GROUP);


    public static void register(){}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block){
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> block, Item.Properties itemProperties){
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), itemProperties));
        return ret;
    }

    private static boolean never(BlockState p_235436_0_, IBlockReader p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }
}
