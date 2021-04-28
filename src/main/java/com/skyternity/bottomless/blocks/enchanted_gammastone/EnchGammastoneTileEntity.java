package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchGammastoneTileEntity extends BlockEntity implements BlockEntityTicker{

    private int[] encIds = {0,1,2};

    public EnchGammastoneTileEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.ENCH_GAMMASTONE_TILEENTITY, pos, state);
    }

    @Override
    public CompoundTag writeNbt(CompoundTag tag) {
        super.writeNbt(tag);

        tag.putIntArray("encIds", this.encIds);

        return tag;
    }

    @Override
    public void readNbt(CompoundTag tag) {
        super.readNbt(tag);
        this.encIds = tag.getIntArray("encIds");
    }

    public int[] getEnchantmentFromTile(){
        return this.encIds;
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {

    }
}
