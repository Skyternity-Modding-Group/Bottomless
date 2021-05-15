package skyternity.bottomless.main.block.porus_shadestone;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import skyternity.bottomless.main.block.ModBlocks;

public class PorusShadestoneTileEntity extends TileEntity{

    private String storedPotionId = "blank";
    private int storedPotionAmount = 0;

    public PorusShadestoneTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public PorusShadestoneTileEntity() {
        this(ModBlocks.PORUS_SHADESTONE_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        this.storedPotionId = tag.getString("storedPotiId");
        this.storedPotionAmount = tag.getInt("storedPotiAmount");
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);

        tag.putString("storedPotiId", this.storedPotionId);
        tag.putInt("storedPotiAmount", this.storedPotionAmount);

        return tag;
    }
}
