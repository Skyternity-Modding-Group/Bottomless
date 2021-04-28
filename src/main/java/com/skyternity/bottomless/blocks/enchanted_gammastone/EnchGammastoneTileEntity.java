package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchGammastoneTileEntity extends BlockEntity implements BlockEntityTicker{

    private String[] containedEncIds = {"blank"};

    public EnchGammastoneTileEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.ENCH_GAMMASTONE_TILEENTITY, pos, state);
    }

    @Override
    public CompoundTag writeNbt(CompoundTag tag) {
        super.writeNbt(tag);

        for (int i = 0; i < this.containedEncIds.length; i++){
            tag.putString("containedEnchant_"+i, this.containedEncIds[i]);
        }

        tag.putInt("containedEnchatnAmount", this.containedEncIds.length);

        return tag;
    }

    @Override
    public void readNbt(CompoundTag tag) {
        super.readNbt(tag);

        int length = tag.getInt("containedEnchatnAmount");
        this.containedEncIds = new String[length];

        for (int i = 0; i < length; i++){
            this.containedEncIds[i] = tag.getString("containedEnchant_"+i);
        }
    }

    public String[] getEnchantmentFromTile(){
        return this.containedEncIds;
    }

    public void putEnchantmentsToTile(String[] enchIds){
        this.containedEncIds = enchIds;
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

    }
}
