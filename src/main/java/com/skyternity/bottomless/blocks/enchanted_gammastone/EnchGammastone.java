package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
        if(!world.isClient && entity instanceof LivingEntity){
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof EnchGammastoneTileEntity){
                String[] enchIds = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentIdsFromTile();
                int[] enchLvls = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentLvlsFromTile();
                if(enchIds != null){
                    Enchantment[] enchantments = new Enchantment[enchIds.length];
                    boolean hasFlame = false;
                    boolean hasBaneOfArthropods = false;
                    float damageModifier = 0;
                    int knockbackLevel = -1;
                    int fireAspectLevel = -1;
                    int lootingLevel = -1;
                    int featherFallingLevel = -1;

                    for (int i = 0; i < enchIds.length; i++){//Get buffs
                        enchantments[i] = Registry.ENCHANTMENT.get(Identifier.tryParse(enchIds[i]));
                        if(enchIds[0] != "blank"){
                            //Outgoing buffs
                            /**
                             * ProtectionEnchantment
                             * RespirationEnchantment
                             * AquaAffinityEnchantment
                             * ThornsEnchantment
                             * DepthStriderEnchantment
                             * FrostWalkerEnchantment
                             * SoulSpeedEnchantment
                             * DamageEnchantment
                             * KnockbackEnchantment
                             * FireAspectEnchantment
                             * LuckEnchantment
                             * SweepingEnchantment
                             * EfficiencyEnchantment
                             * SilkTouchEnchantment
                             * UnbreakingEnchantment
                             * PowerEnchantment
                             * PunchEnchantment
                             * FlameEnchantment
                             * InfinityEnchantment
                             * LuckEnchantment
                             * LureEnchantment
                             * LoyaltyEnchantment
                             * ImpalingEnchantment
                             * RiptideEnchantment
                             * ChannelingEnchantment
                             * MultishotEnchantment
                             * QuickChargeEnchantment
                             * PiercingEnchantment
                             * MendingEnchantment
                             *
                             * VanishingCurseEnchantment
                             * BindingCurseEnchantment
                             */

                            if(enchantments[i] instanceof DamageEnchantment){
                                damageModifier = enchantments[i].getAttackDamage(enchLvls[i], EntityGroup.DEFAULT);
                                if(enchantments[i] == Enchantments.BANE_OF_ARTHROPODS){
                                    hasBaneOfArthropods = true;
                                }
                            }
                            if(enchantments[i] instanceof FlameEnchantment){
                                hasFlame = true;
                            }
                            if(enchantments[i] instanceof FireAspectEnchantment){
                                fireAspectLevel = enchLvls[i];
                            }
                            if(enchantments[i] instanceof KnockbackEnchantment){
                                knockbackLevel = enchLvls[i];
                            }
                            if(enchantments[i] instanceof LuckEnchantment){
                                if(enchantments[i] == Enchantments.LOOTING || enchantments[i] == Enchantments.FORTUNE){
                                    lootingLevel = enchLvls[i];
                                }
                            }
                            if(enchantments[i] instanceof ProtectionEnchantment){
                                if(((ProtectionEnchantment)enchantments[i]).protectionType == ProtectionEnchantment.Type.FALL){

                                }
                            }
                            //Incoming buffs

                            /**
                             * - : has texture
                             * | : has code
                             * [] : other method for effects
                             * ? : what should it do?
                             *
                             * ?minecraft:protection
                             * ?minecraft:fire_protection
                             * minecraft:feather_falling [fallOn]
                             * -minecraft:blast_protection
                             * ?minecraft:projectile_protection
                             * ?minecraft:respiration
                             * ?minecraft:aqua_affinity
                             * |minecraft:thorns
                             * ?minecraft:depth_strider
                             * minecraft:frost_walker
                             * minecraft:binding_curse
                             * ?minecraft:soul_speed
                             * |-minecraft:sharpness
                             * |-minecraft:smite
                             * |minecraft:bane_of_arthropods
                             * |minecraft:knockback
                             * |-minecraft:fire_aspect
                             * minecraft:looting
                             * |minecraft:sweeping
                             * ?minecraft:efficiency
                             * ?minecraft:silk_touch
                             * minecraft:unbreaking
                             * |minecraft:fortune
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
                             * ?minecraft:mending
                             * minecraft:vanishing_curse
                             */
                        }
                    }
                    for (int i = 0; i < enchIds.length; i++){ //apply outgoing effects
                        if(enchIds[0] != "blank"){
                            if(enchantments[i] == Enchantments.THORNS) {
                                entity.damage(DamageSource.GENERIC, ThornsEnchantment.getDamageAmount(enchLvls[i], world.random) + damageModifier);
                                if(hasBaneOfArthropods && ((LivingEntity) entity).getGroup() == EntityGroup.ARTHROPOD){
                                    ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,5, 3));
                                }
                                if(knockbackLevel >= 1){
                                    ((LivingEntity)entity).takeKnockback(knockbackLevel, entity.getBlockPos().getX(), entity.getBlockPos().getZ());
                                }
                                if(fireAspectLevel >= 1){
                                    entity.setOnFireFor(fireAspectLevel * 4);
                                }
                                if(lootingLevel >= 1){
                                    //It seems like its impossible to implement looting enchantment =(
                                }
                            }

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
            boolean hasVanishing = false;
            if(enchIds != null){
                Enchantment[] enchantments = new Enchantment[enchIds.length];
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.get(dropStack);
                for (int i = 0; i < enchIds.length; i++){
                    enchantments[i] = Registry.ENCHANTMENT.get(Identifier.tryParse(enchIds[i]));
                    if(enchIds[0] != "blank"){
                        enchMap.put(enchantments[i], enchLvls[i]);
                    }
                    if(enchantments[i] == Enchantments.VANISHING_CURSE){
                        hasVanishing = true;
                    }
                }
                EnchantmentHelper.set(enchMap, dropStack);
            }

            if(!hasVanishing){
                ItemEntity dropStackEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), dropStack);
                world.spawnEntity(dropStackEntity);
                dropStackEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
            }
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
