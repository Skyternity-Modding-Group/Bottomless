package skyternity.bottomless.main.block.porus_shadestone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import skyternity.bottomless.main.block.ModBlocks;

public class PorusShadestoneBlock extends Block {

    public PorusShadestoneBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return ModBlocks.PORUS_SHADESTONE_TILE.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        ItemStack handStack = playerEntity.getItemInHand(hand);
        if(handStack.getItem() == Items.POTION){
            TileEntity tile = world.getBlockEntity(pos);
            String potiId = Registry.POTION.getKey(PotionUtils.getPotion(handStack)).toString();
            CompoundNBT nbt = tile.getTileData();
            nbt.putString("storedPotiId", potiId);
            nbt.putInt("storedPotiAmount", 1000);
            tile.setChanged();
            if(!playerEntity.isCreative()){
                handStack.shrink(1);
                playerEntity.addItem(new ItemStack(Items.GLASS_BOTTLE));
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
