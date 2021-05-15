package skyternity.bottomless.main.block.geyser;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import skyternity.bottomless.main.block.ModBlocks;
import skyternity.bottomless.main.block.porus_shadestone.PorusShadestoneTileEntity;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeyserTileEntity extends TileEntity implements ITickableTileEntity {

    public GeyserTileEntity(TileEntityType<?> tileEntity) {
        super(tileEntity);
    }

    public GeyserTileEntity() {
        this(ModBlocks.GEYSER_TILE.get());
    }


    @Override
    public void tick() {
        World world = this.getLevel();
        BlockPos pos = this.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if(state == state.getBlock().defaultBlockState().setValue(GeyserBlock.WATERLOGGED, true)){
            BlockState blockBelow = world.getBlockState(pos.below());
            if(blockBelow.getBlock() == ModBlocks.POROUS_SHADESTONE.get()){
                TileEntity tile = world.getBlockEntity(pos.below());
                CompoundNBT nbt = tile.getTileData();
                String potionId = nbt.getString("storedPotiId");
                int potionAmount = nbt.getInt("storedPotiAmount");

                Vector3d pos1 = GeyserBlock.offset(pos, 12D, 10D, 5D);
                int particleSpotChance = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                if(particleSpotChance == 1){
                    pos1 = GeyserBlock.offset(pos, 12D, 10D, 5D);
                }else if(particleSpotChance == 2){
                    pos1 = GeyserBlock.offset(pos, 6D, 7D, 6D);
                }else if(particleSpotChance == 3){
                    pos1 = GeyserBlock.offset(pos, 9D, 5D, 12D);
                }
                if(potionId != "blank"){
                    Potion potion = Registry.POTION.get(ResourceLocation.tryParse(potionId));
                    IParticleData iparticledata = ParticleTypes.ENTITY_EFFECT;


                    if(potionAmount > 1){
                        int l1 = PotionUtils.getColor(potion);
                        int i2 = l1 >> 16 & 255;
                        int j2 = l1 >> 8 & 255;
                        int j1 = l1 & 255;
                        this.level.addAlwaysVisibleParticle(iparticledata, pos1.x, pos1.y, pos1.z, (double)((float)i2 / 255.0F), (double)((float)j2 / 255.0F), (double)((float)j1 / 255.0F));

                        double x = (double)this.getBlockPos().getX();
                        double y = (double)this.getBlockPos().getY();
                        double z = (double)this.getBlockPos().getZ();
                        AxisAlignedBB scanAboveAABB = new AxisAlignedBB(x, y, z, x+ 1.0d, y+ 3.0d, z+ 1.0d);

                        List<? extends Entity> entitiesAboveGeyser = world.getEntities((Entity)null, scanAboveAABB);
                        if(!entitiesAboveGeyser.isEmpty()){
                            System.out.println("Lol, somebody is inside the bounding box");
                            for (int i = 0; i < entitiesAboveGeyser.size(); i++){
                                Entity entity = entitiesAboveGeyser.get(i);
                                if(entity instanceof LivingEntity){
                                    ((LivingEntity)entity).addEffect(new EffectInstance(potion.getEffects().stream().findFirst().get()));
                                    --potionAmount;
                                }
                            }
                        }
                    }else{
                        world.addParticle(ParticleTypes.BUBBLE, pos1.x, pos1.y, pos1.z, pos.getX(), pos.getY(), pos.getZ());
                    }

                    nbt.putInt("storedPotiAmount", potionAmount);
                    System.out.println("POTION AMOUNT IS " + potionAmount + " IN " + tile);
                    tile.setChanged();
                }
            }
        }
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        return super.save(p_189515_1_);
    }
}
