package com.skyternity.bottomless.Blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class GwowwyBwocc extends Block {
    public static final BooleanProperty LIT;
    private static final DustParticleEffect VOID_DUST;

    public GwowwyBwocc(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        light(state, world, pos);
        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        light(world.getBlockState(pos), world, pos);
        super.onSteppedOn(world, pos, entity);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            spawnParticles(world, pos);
        } else {
            light(state, world, pos);
        }
        ItemStack itemStack = player.getStackInHand(hand);
        return itemStack.getItem() instanceof BlockItem && (new ItemPlacementContext(player, hand, itemStack, hit)).canPlace() ? ActionResult.PASS : ActionResult.SUCCESS;
    }

    private void light(BlockState state, World world, BlockPos pos) {
        spawnParticles(world, pos);
        if (!(Boolean)state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, true), 3);
            world.getBlockTickScheduler().schedule(pos, this, world.random.nextInt(20));
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LIT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        this.scheduledTick(state, world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if (state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, false), 3);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            spawnParticles(world, pos);
        }
    }

    private static void spawnParticles(World world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getOffsetX() : (double) world.random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getOffsetY() : (double) world.random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getOffsetZ() : (double) world.random.nextFloat();
                world.addParticle(VOID_DUST, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    static {
        LIT = Properties.LIT;
        VOID_DUST = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0x0892d0)), 1.0F);
    }
}
