package skyternity.bottomless.main.block.gammastone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import skyternity.bottomless.main.block.ModBlocks;

public class GammastoneBricksBlock extends Block {

    public GammastoneBricksBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(!world.isClientSide){
            ItemStack bookStack = player.getItemInHand(hand);
            if(bookStack.getItem() instanceof EnchantedBookItem){

                ListNBT listTag = EnchantedBookItem.getEnchantments(bookStack);
                String[] enchIds = new String[listTag.size()];
                int[] enchLvls = new int[listTag.size()];
                for (int i = 0; i < listTag.size(); i++){
                    CompoundNBT bookNBT = EnchantedBookItem.getEnchantments(bookStack).getCompound(i);
                    enchIds[i] = bookNBT.getString("id");
                    enchLvls[i] = bookNBT.getInt("lvl");
                }

                if(player.isCreative()){
                    world.setBlockAndUpdate(pos, ModBlocks.ENCHANTED_GAMMASTONE_BRICKS.get().defaultBlockState());
                    TileEntity tileEntity = world.getBlockEntity(pos);
                    EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                    stoneTe.putEnchantmentIdsToTile(enchIds);
                    stoneTe.putEnchantmentLvlsToTile(enchLvls);
                    world.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    return ActionResultType.SUCCESS;
                }else{
                    if(player.experienceLevel >= 15){
                        player.giveExperiencePoints(-255);
                        world.setBlockAndUpdate(pos, ModBlocks.ENCHANTED_GAMMASTONE_BRICKS.get().defaultBlockState());
                        TileEntity tileEntity = world.getBlockEntity(pos);
                        EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                        stoneTe.putEnchantmentIdsToTile(enchIds);
                        stoneTe.putEnchantmentLvlsToTile(enchLvls);
                        world.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return ActionResultType.SUCCESS;
                    }
                }

            }

        }
        return ActionResultType.PASS;
    }
}
