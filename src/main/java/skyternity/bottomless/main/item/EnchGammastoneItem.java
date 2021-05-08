package skyternity.bottomless.main.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import skyternity.bottomless.main.block.gammastone.EnchGammastoneTileEntity;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class EnchGammastoneItem extends BlockItem {

    public EnchGammastoneItem(Block block, Properties properties) { super(block, properties); }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        if(!world.isClientSide && stack.isEnchanted()){
            TileEntity tileEntity = world.getBlockEntity(pos);

            if(tileEntity instanceof EnchGammastoneTileEntity){
                EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(stack);
                if(enchMap != null){
                    Set<Enchantment> enchSet = enchMap.keySet();
                    Enchantment[] enchArray = enchSet.toArray(new Enchantment[enchSet.size()]);
                    String[] enchIds = new String[enchMap.size()];
                    int[] enchLvls = new int[enchMap.size()];
                    for (int i = 0; i < enchMap.size(); i++){
                        enchIds[i] = Registry.ENCHANTMENT.getKey(enchArray[i]).toString();
                        enchLvls[i] = enchMap.get(enchArray[i]);
                    }
                    stoneTe.putEnchantmentIdsToTile(enchIds);
                    stoneTe.putEnchantmentLvlsToTile(enchLvls);
                }

            }
        }

        return updateCustomBlockEntityTag(world, player, pos, stack);
    }
}
