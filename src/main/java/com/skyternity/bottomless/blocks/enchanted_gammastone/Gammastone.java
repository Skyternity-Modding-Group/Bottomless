package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Gammastone extends Block {

    public Gammastone(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            ItemStack bookStack = player.getStackInHand(hand);
            if(bookStack.getItem() instanceof EnchantedBookItem){

                ListTag listTag = EnchantedBookItem.getEnchantmentNbt(bookStack);
                String[] enchIds = new String[listTag.size()];
                int[] enchLvls = new int[listTag.size()];
                for (int i = 0; i < listTag.size(); i++){
                    CompoundTag bookNBT = EnchantedBookItem.getEnchantmentNbt(bookStack).getCompound(i);
                    enchIds[i] = bookNBT.getString("id");
                    enchLvls[i] = bookNBT.getInt("lvl");
                }

                if(player.isCreative()){
                    world.setBlockState(pos, BlockRegistry.ENCHANTED_GAMMASTONE_BRICKS.getDefaultState());
                    BlockEntity tileEntity = world.getBlockEntity(pos);
                    EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                    stoneTe.putEnchantmentIdsToTile(enchIds);
                    stoneTe.putEnchantmentLvlsToTile(enchLvls);
                    return ActionResult.SUCCESS;
                }else{
                    if(player.experienceLevel >= 15){
                        player.addExperience(-255);
                        world.setBlockState(pos, BlockRegistry.ENCHANTED_GAMMASTONE_BRICKS.getDefaultState());
                        BlockEntity tileEntity = world.getBlockEntity(pos);
                        EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                        stoneTe.putEnchantmentIdsToTile(enchIds);
                        stoneTe.putEnchantmentLvlsToTile(enchLvls);
                        return ActionResult.SUCCESS;
                    }
                }

            }

        }
        return ActionResult.PASS;
    }

}
