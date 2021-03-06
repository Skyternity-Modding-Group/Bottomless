package com.skyternity.bottomless.blocks;

import com.skyternity.bottomless.BottomlessMain;
import com.skyternity.bottomless.blocks.geyser.GeyserSource;
import com.skyternity.bottomless.entities.PorousShadestoneEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PorousShadestone extends BlockWithEntity implements GeyserSource {
    public PorousShadestone(Settings settings) {
        super(settings);
    }

    @Override
    public <T extends LivingEntity> void applyGeyserEffect(BlockState state, World world, BlockPos pos, T entity) {
        PorousShadestoneEntity blockEntity = (PorousShadestoneEntity) world.getBlockEntity(pos);
        assert blockEntity != null;

        Potion potion = Registry.POTION.get(new Identifier(blockEntity.potion));
        potion.getEffects().forEach(effect ->
                entity.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), 50)));
    }

    @Override
    public boolean isApplyingEffect(BlockState state, World world, BlockPos pos) {
        //When ya debug, please at least comment them out when ya done... -laz
        //BottomlessMain.LOGGER.info("Recv pos" + pos);
        PorousShadestoneEntity blockEntity = (PorousShadestoneEntity) world.getBlockEntity(pos);
        assert blockEntity != null;

        return !blockEntity.potion.equals("minecraft:empty");
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PorousShadestoneEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        PorousShadestoneEntity entity = (PorousShadestoneEntity) world.getBlockEntity(pos);
        assert entity != null;

        boolean success = stack.getItem() instanceof PotionItem;
        if (!success) {
            return ActionResult.PASS;
        } else {
            entity.setPotion(stack);

            return ActionResult.SUCCESS;
        }
    }
}
