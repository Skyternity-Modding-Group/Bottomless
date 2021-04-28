package com.skyternity.bottomless.items;

import com.skyternity.bottomless.blocks.enchanted_gammastone.EnchGammastoneTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class EnchGammastoneItem extends BlockItem {

    public EnchGammastoneItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if(!world.isClient && stack.hasEnchantments()){
            BlockEntity tileEntity = world.getBlockEntity(pos);

            if(tileEntity instanceof EnchGammastoneTileEntity){
                EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(stack);
                if(enchMap != null){
                    Set<Enchantment> enchSet = enchMap.keySet();
                    Enchantment[] enchArray = enchSet.toArray(new Enchantment[enchSet.size()]);
                    String[] enchIds = new String[enchMap.size()];
                    for (int i = 0; i < enchMap.size(); i++){
                        enchIds[i] = Registry.ENCHANTMENT.getId(enchArray[i]).toString();
                    }
                    stoneTe.putEnchantmentsToTile(enchIds);
                }

            }
        }

        return writeTagToBlockEntity(world, player, pos, stack);
    }

}
