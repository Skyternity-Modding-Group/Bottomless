package skyternity.bottomless.main.block.gammastone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.*;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import skyternity.bottomless.main.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class EnchGammastoneBricksBlock extends Block {

    private static final VoxelShape SHAPE_ALMOSTFULL = Block.box(0.9D, 0.9D, 0.9D, 15.9D, 15.9D, 15.9D);

    public EnchGammastoneBricksBlock(Properties properties) { super(properties); }

    @Override
    public boolean hasTileEntity(BlockState state) { return true; }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) { return ModBlocks.ENCHANTED_GAMMASTONE_BRICKS_TILE.get().create(); }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return SHAPE_ALMOSTFULL;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if(!world.isClientSide){
            TileEntity tileEntity = world.getBlockEntity(pos);
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
                        enchantments[i] = Registry.ENCHANTMENT.get(ResourceLocation.tryParse(enchIds[i]));
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
                                damageModifier = enchantments[i].getDamageBonus(enchLvls[i], CreatureAttribute.UNDEFINED);
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
                            if(enchantments[i] instanceof LootBonusEnchantment){
                                if(enchantments[i] == Enchantments.MOB_LOOTING || enchantments[i] == Enchantments.BLOCK_FORTUNE){
                                    lootingLevel = enchLvls[i];
                                }
                            }
                            if(enchantments[i] instanceof ProtectionEnchantment){
                                if(((ProtectionEnchantment)enchantments[i]).type == ProtectionEnchantment.Type.ALL){
                                    if(protectionLevel == -1){
                                        protectionLevel = enchLvls[i];
                                    }else{
                                        protectionLevel += enchLvls[i];
                                    }
                                }else if(((ProtectionEnchantment)enchantments[i]).type == ProtectionEnchantment.Type.FIRE){

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
                            if(((LivingEntity) entity).canUpdate()){
                                entity.hurt(new DamageSource("enchanted_gammastone"), ThornsEnchantment.getDamage(thornsLevel, world.random) + damageModifier);
                                world.playSound(null, pos, SoundEvents.THORNS_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                                durabilityDamageModifier++;
                                if(hasBaneOfArthropods && ((LivingEntity) entity).getMobType() == CreatureAttribute.ARTHROPOD){
                                    ((LivingEntity)entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,5, 3));
                                }
                                if(knockbackLevel >= 1){
                                    ((LivingEntity)entity).knockback(knockbackLevel, entity.blockPosition().getX(), entity.blockPosition().getZ());
                                }
                                if(fireAspectLevel >= 1){
                                    entity.setSecondsOnFire(fireAspectLevel * 4);
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
                                int additionalHp = ((EnchGammastoneTileEntity) tileEntity).getBlockHealth() + (((ExperienceOrbEntity)entity).getValue() + (2*mendingLevel));
                                ((EnchGammastoneTileEntity) tileEntity).putBlockHealth(additionalHp);
                                entity.remove(false);
                                world.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.6f, 1.0f);
                            }
                        }

                    }


                }else if(blockHP == 0){
                    ItemEntity dropStackEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.GAMMASTONE_BRICKS.get()));
                    world.addFreshEntity(dropStackEntity);
                    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    dropStackEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
                }else if(timer >= 0){
                    timer--;
                    ((EnchGammastoneTileEntity) tileEntity).putDamageTimer(timer);
                }

            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(!world.isClientSide){
            TileEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof EnchGammastoneTileEntity){
                EnchGammastoneTileEntity stoneTe = (EnchGammastoneTileEntity) tileEntity;
                ItemStack bookStack = player.getItemInHand(hand);
                if(bookStack.getItem() instanceof EnchantedBookItem){
                    ListNBT listTag = EnchantedBookItem.getEnchantments(bookStack);
                    String[] enchIds = new String[listTag.size()];
                    int[] enchLvls = new int[listTag.size()];
                    for (int i = 0; i < listTag.size(); i++){
                        CompoundNBT bookNBT = EnchantedBookItem.getEnchantments(bookStack).getCompound(i);
                        enchIds[i] = bookNBT.getString("id");
                        enchLvls[i] = bookNBT.getInt("lvl");
                    }

                    if(player.isCreative()){
                        stoneTe.putEnchantmentIdsToTile(enchIds);
                        stoneTe.putEnchantmentLvlsToTile(enchLvls);
                        world.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        return ActionResultType.SUCCESS;
                    }else{
                        if(player.experienceLevel >= 15){
                            player.giveExperiencePoints(-255);
                            stoneTe.putEnchantmentIdsToTile(enchIds);
                            stoneTe.putEnchantmentLvlsToTile(enchLvls);
                            world.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                            return ActionResultType.SUCCESS;
                        }
                    }

                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
        if(tileEntity instanceof EnchGammastoneTileEntity){
            TileEntity enchGammastoneTe = (EnchGammastoneTileEntity)tileEntity;
            ItemStack dropStack = new ItemStack(ModBlocks.ENCHANTED_GAMMASTONE_BRICKS.get().asItem());
            String[] enchIds = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentIdsFromTile();
            int[] enchLvls = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentLvlsFromTile();
            boolean hasVanishing = false;
            if(enchIds != null){
                Enchantment[] enchantments = new Enchantment[enchIds.length];
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(dropStack);
                for (int i = 0; i < enchIds.length; i++){
                    enchantments[i] = Registry.ENCHANTMENT.get(ResourceLocation.tryParse(enchIds[i]));
                    if(enchIds[0] != "blank"){
                        enchMap.put(enchantments[i], enchLvls[i]);
                    }
                    if(enchantments[i] == Enchantments.VANISHING_CURSE){
                        hasVanishing = true;
                    }
                }
                EnchantmentHelper.setEnchantments(enchMap, dropStack);
            }

            if(!hasVanishing){
                ItemEntity dropStackEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), dropStack);
                world.addFreshEntity(dropStackEntity);
                dropStackEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        TileEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof EnchGammastoneTileEntity){
            TileEntity enchGammastoneTe = blockEntity;
            ItemStack dropStack = new ItemStack(ModBlocks.ENCHANTED_GAMMASTONE_BRICKS.get().asItem());
            String[] enchIds = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentIdsFromTile();
            int[] enchLvls = ((EnchGammastoneTileEntity) enchGammastoneTe).getEnchantmentLvlsFromTile();
            if(enchIds != null){
                Enchantment[] enchantments = new Enchantment[enchIds.length];
                Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(dropStack);
                for (int i = 0; i < enchIds.length; i++){
                    enchantments[i] = Registry.ENCHANTMENT.get(ResourceLocation.tryParse(enchIds[i]));
                    if(enchIds[0] != "blank"){
                        enchMap.put(enchantments[i], enchLvls[i]);
                    }
                }
                EnchantmentHelper.setEnchantments(enchMap, dropStack);
                //dropStack? why are you not enchanted????
                return dropStack;
            }

        }
        System.out.println("returned new stack");
        return new ItemStack(world.getBlockState(pos).getBlock().asItem());
    }
}
