package com.skyternity.bottomless.entities;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class PorousShadestoneEntity extends BlockEntity implements BlockEntityClientSerializable {
    public String potion = "minecraft:empty";

    public PorousShadestoneEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.POROUS_SHADESTONE_ENTITY, pos, state);
    }

    public void setPotion(ItemStack stack) {
        Potion potion = PotionUtil.getPotion(stack);
        Identifier potionId = Registry.POTION.getId(potion);

        this.potion = potionId.toString();
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        potion = tag.getString("potion");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        tag.putString("potion", potion);

        return tag;
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        potion = tag.getString("potion");
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        super.writeNbt(tag);

        tag.putString("potion", potion);

        return tag;
    }
}
