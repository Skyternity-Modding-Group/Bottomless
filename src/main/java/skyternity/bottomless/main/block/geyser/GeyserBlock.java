package skyternity.bottomless.main.block.geyser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import skyternity.bottomless.main.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class GeyserBlock extends Block implements IWaterLoggable{

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_1 = Block.box(8.0D, 0.0D, 1.0D, 15.0D, 10.0D, 8.0D);
    private static final VoxelShape SHAPE_2 = Block.box(2.0D, 0.0D, 2.0D, 8.0D, 7.0D, 8.0D);
    private static final VoxelShape SHAPE_3 = Block.box(5.0D, 0.0D, 8.0D, 12.0D, 5.0D, 15.0D);
    private static final VoxelShape SHAPE = VoxelShapes.or(SHAPE_1, SHAPE_2, SHAPE_3);

    public GeyserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(state == this.defaultBlockState().setValue(WATERLOGGED, true)){
            BlockState blockBelow = world.getBlockState(pos.below());
            if(blockBelow.getBlock() != ModBlocks.POROUS_SHADESTONE.get()){
                Vector3d pos1 = offset(pos, 12D, 10D, 5D);
                particleSpout(world, pos1, pos);
            }
        }
    }

    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModBlocks.GEYSER_TILE.get().create();
    }

    public void particleSpout(World world, Vector3d pos, BlockPos blockPos) {
        world.addParticle(ParticleTypes.ENTITY_EFFECT, pos.x, pos.y, pos.z, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static Vector3d offset(BlockPos pos, double x, double y, double z) {
        return new Vector3d(
                (double) pos.getX() + hexdec(x),
                (double) pos.getY() + hexdec(y),
                (double) pos.getZ() + hexdec(z)
        );
    }
    public static double hexdec(int n) {
        return hexdec((double) n);
    }
    public static double hexdec(double n) {
        return n / 16D;
    }

}
