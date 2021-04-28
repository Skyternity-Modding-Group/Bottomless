package com.skyternity.bottomless.blocks.enchanted_gammastone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnchGammastone extends Block implements BlockEntityProvider {

    private static final VoxelShape SHAPE_ALMOSTFULL = Block.createCuboidShape(0.9D, 0.9D, 0.9D, 15.9D, 15.9D, 15.9D);


    public EnchGammastone(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnchGammastoneTileEntity(pos, state);
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_ALMOSTFULL;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClient){
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof EnchGammastoneTileEntity){
                int[] enchIds = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentFromTile();
                Enchantment[] enchantments = new Enchantment[enchIds.length];
                for (int i = 0; i < enchIds.length; i++){
                    enchantments[i] = Enchantment.byRawId(enchIds[i]);
                    System.out.println("Block's Stored enchants: " + enchantments[i].getName(enchantments[i].getMinLevel()).toString());
                }
            }
        }

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof EnchGammastoneTileEntity){
                ItemStack bookStack = player.getStackInHand(hand);
                if(bookStack.getItem() instanceof EnchantedBookItem){
                    ListTag listTag = EnchantedBookItem.getEnchantmentNbt(bookStack);
                    for (int i = 0; i < listTag.stream().count(); i++){
                        CompoundTag bookNBT = EnchantedBookItem.getEnchantmentNbt(bookStack).getCompound(i);
                        System.out.println(bookNBT);
                    }
                    return ActionResult.success(world.isClient);
                }
            }
        }
        return ActionResult.PASS;
    }
}
