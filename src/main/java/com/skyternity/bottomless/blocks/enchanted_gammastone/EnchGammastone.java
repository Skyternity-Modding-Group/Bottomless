package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import java.util.concurrent.ThreadLocalRandom;

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

    //This section of the code is protected by The LAZ Seal. if you want to touch it, then dont. thats what it means. only Laz is approved of touching this, cuz only he knows how it works.
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClient){
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof EnchGammastoneTileEntity){
                String[] enchIds = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentIdsFromTile();
                int[] enchLvls = ((EnchGammastoneTileEntity) tileEntity).getEnchantmentLvlsFromTile();
                int blockHP = ((EnchGammastoneTileEntity) tileEntity).getBlockHealth();
                int timer = ((EnchGammastoneTileEntity) tileEntity).getDamageTimer();
                if(enchIds != null && blockHP > 0 && timer <= 0){
                    Enchantment[] enchantments = new Enchantment[enchIds.length];
                    boolean hasFlame = false;
                    boolean hasBaneOfArthropods = false;
                    int thornsLevel = -1;
                    float damageModifier = 0;
                    int knockbackLevel = -1;
                    int fireAspectLevel = -1;
                    int lootingLevel = -1;
                    int protectionLevel = -1;
                    int mendingLevel = -1;
                    int durabilityDamageModifier = 0;

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

                            if(enchantments[i] == Enchantments.THORNS){
                                thornsLevel = enchLvls[i];
                            }
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
                                if(((ProtectionEnchantment)enchantments[i]).protectionType == ProtectionEnchantment.Type.ALL){
                                    if(protectionLevel == -1){
                                        protectionLevel = enchLvls[i];
                                    }else{
                                        protectionLevel += enchLvls[i];
                                    }
                                }else if(((ProtectionEnchantment)enchantments[i]).protectionType == ProtectionEnchantment.Type.FIRE){

                                }
                            }
                            if(enchantments[i] instanceof UnbreakingEnchantment){
                                if(protectionLevel == -1){
                                    protectionLevel = enchLvls[i];
                                }else{
                                    protectionLevel += enchLvls[i];
                                }
                            }
                            if(enchantments[i] instanceof MendingEnchantment){
                                mendingLevel = enchLvls[i];
                            }
                            //Incoming buffs

                            /**
                             * - : has texture
                             * | : has code
                             * [] : other method for effects
                             * ? : what should it do?
                             *
                             * |minecraft:protection
                             * minecraft:fire_protection
                             * minecraft:feather_falling [fallOn]
                             * -minecraft:blast_protection
                             * minecraft:projectile_protection
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
                             * |minecraft:unbreaking
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
                             * |minecraft:mending
                             * |minecraft:vanishing_curse
                             */
                        }
                    }
                    if(enchIds[0] != "blank"){
                        if(thornsLevel >= 1 && entity instanceof LivingEntity) {
                            if(((LivingEntity) entity).canTakeDamage()){
                                entity.damage(DamageSource.GENERIC, ThornsEnchantment.getDamageAmount(thornsLevel, world.random) + damageModifier);
                                world.playSound(null, pos, SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                                durabilityDamageModifier++;
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
                                if(protectionLevel >= 1){
                                    if(protectionLevel >= durabilityDamageModifier){
                                        int randomChance = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                                        if(randomChance >= 50 - (6*protectionLevel)){
                                            if(protectionLevel - durabilityDamageModifier <= 0){
                                                durabilityDamageModifier = 1;
                                            }else{
                                                durabilityDamageModifier = protectionLevel - durabilityDamageModifier;
                                            }
                                        }else{
                                            durabilityDamageModifier = 0;
                                        }
                                    }else{
                                        durabilityDamageModifier = durabilityDamageModifier - protectionLevel;
                                    }
                                }

                                ((EnchGammastoneTileEntity) tileEntity).putBlockHealth(blockHP-durabilityDamageModifier);
                                ((EnchGammastoneTileEntity) tileEntity).putDamageTimer(10);
                            }
                        }else if(entity instanceof ExperienceOrbEntity){
                            if(mendingLevel >= 1 && blockHP < 1000){
                                blockHP =+ (((ExperienceOrbEntity)entity).getExperienceAmount() + (2*mendingLevel));
                                ((EnchGammastoneTileEntity) tileEntity).putBlockHealth(blockHP);
                                entity.remove(Entity.RemovalReason.DISCARDED);
                                world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.6f, 1.0f);
                            }
                        }

                    }


                }else if(blockHP == 0){
                    ItemEntity dropStackEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.GAMMASTONE_BRICKS));
                    world.spawnEntity(dropStackEntity);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    dropStackEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
                }else if(timer >= 0){
                    timer--;
                    ((EnchGammastoneTileEntity) tileEntity).putDamageTimer(timer);
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
                    NbtList listTag = EnchantedBookItem.getEnchantmentNbt(bookStack);
                    String[] enchIds = new String[listTag.size()];
                    int[] enchLvls = new int[listTag.size()];
                    for (int i = 0; i < listTag.size(); i++){
                        NbtCompound bookNBT = EnchantedBookItem.getEnchantmentNbt(bookStack).getCompound(i);
                        enchIds[i] = bookNBT.getString("id");
                        enchLvls[i] = bookNBT.getInt("lvl");
                    }

                    if(player.isCreative()){
                        stoneTe.putEnchantmentIdsToTile(enchIds);
                        stoneTe.putEnchantmentLvlsToTile(enchLvls);
                        world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return ActionResult.SUCCESS;
                    }else{
                        if(player.experienceLevel >= 15){
                            player.addExperience(-255);
                            stoneTe.putEnchantmentIdsToTile(enchIds);
                            stoneTe.putEnchantmentLvlsToTile(enchLvls);
                            world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
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
