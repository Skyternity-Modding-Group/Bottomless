package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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
                String[] enchIds = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentIdsFromTile();
                int[] enchLvls = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentLvlsFromTile();
                if(enchIds != null){
                    Enchantment[] enchantments = new Enchantment[enchIds.length];
                    for (int i = 0; i < enchIds.length; i++){
                        enchantments[i] = Registry.ENCHANTMENT.get(Identifier.tryParse(enchIds[i]));
                        if(enchIds[0] != "blank"){
                            if(enchantments[i] == Enchantments.THORNS){
                                ThornsEnchantment.getDamageAmount(enchLvls[i], world.random);
                            }
                            /**
                             * minecraft:protection
                             * minecraft:fire_protection
                             * minecraft:feather_falling
                             * -minecraft:blast_protection
                             * minecraft:projectile_protection
                             * minecraft:respiration
                             * minecraft:aqua_affinity
                             * minecraft:thorns
                             * minecraft:depth_strider
                             * minecraft:frost_walker
                             * minecraft:binding_curse
                             * minecraft:soul_speed
                             * -minecraft:sharpness
                             * -minecraft:smite
                             * minecraft:bane_of_arthropods
                             * minecraft:knockback
                             * -minecraft:fire_aspect
                             * minecraft:looting
                             * minecraft:sweeping
                             * minecraft:efficiency
                             * minecraft:silk_touch
                             * minecraft:unbreaking
                             * minecraft:fortune
                             * minecraft:power
                             * minecraft:punch
                             * -minecraft:flame
                             * minecraft:infinity
                             * minecraft:luck_of_the_sea
                             * minecraft:lure
                             * minecraft:loyalty
                             * minecraft:impaling
                             * minecraft:riptide
                             * minecraft:channeling
                             * minecraft:multishot
                             * minecraft:quick_charge
                             * minecraft:piercing
                             * minecraft:mending
                             * minecraft:vanishing_curse
                             */
                        }
                    }
                }

            }
        }

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof EnchGammastoneTileEntity){
                EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
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
                        stoneTe.putEnchantmentIdsToTile(enchIds);
                        stoneTe.putEnchantmentLvlsToTile(enchLvls);
                        return ActionResult.SUCCESS;
                    }else{
                        if(player.experienceLevel >= 15){
                            player.addExperience(-255);
                            stoneTe.putEnchantmentIdsToTile(enchIds);
                            stoneTe.putEnchantmentLvlsToTile(enchLvls);
                            return ActionResult.SUCCESS;
                        }
                    }

                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        if(blockEntity instanceof EnchGammastoneTileEntity){
            BlockEntity enchGammastoneTe = (EnchGammastoneTileEntity)blockEntity;
            ItemStack dropStack = new ItemStack(BlockRegistry.ENCHANTED_GAMMASTONE_BRICKS.asItem());
            String[] enchIds = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentIdsFromTile();
            int[] enchLvls = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentLvlsFromTile();
            if(enchIds != null){
                Enchantment[] enchantments = new Enchantment[enchIds.length];
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(dropStack);
                for (int i = 0; i < enchIds.length; i++){
                    enchantments[i] = Registry.ENCHANTMENT.get(Identifier.tryParse(enchIds[i]));
                    if(enchIds[0] != "blank"){
                        enchMap.put(enchantments[i], enchLvls[i]);
                    }
                }
                EnchantmentHelper.set(enchMap, dropStack);
            }

            ItemEntity dropStackEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), dropStack);
            world.spawnEntity(dropStackEntity);
            dropStackEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof EnchGammastoneTileEntity){
            BlockEntity enchGammastoneTe = blockEntity;
            ItemStack dropStack = new ItemStack(BlockRegistry.ENCHANTED_GAMMASTONE_BRICKS.asItem());
            String[] enchIds = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentIdsFromTile();
            int[] enchLvls = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentLvlsFromTile();
            if(enchIds != null){
                Enchantment[] enchantments = new Enchantment[enchIds.length];
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(dropStack);
                for (int i = 0; i < enchIds.length; i++){
                    enchantments[i] = Registry.ENCHANTMENT.get(Identifier.tryParse(enchIds[i]));
                    if(enchIds[0] != "blank"){
                        enchMap.put(enchantments[i], enchLvls[i]);
                    }
                }
                EnchantmentHelper.set(enchMap, dropStack);
                //dropStack? why are you not enchanted????
                return dropStack;
            }

        }
        System.out.println("returned new stack");
        return new ItemStack(world.getBlockState(pos).getBlock().asItem());
    }
}
