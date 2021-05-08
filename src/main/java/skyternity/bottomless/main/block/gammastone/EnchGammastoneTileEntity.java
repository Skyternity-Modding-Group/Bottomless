package skyternity.bottomless.main.block.gammastone;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import skyternity.bottomless.main.block.ModBlocks;

public class EnchGammastoneTileEntity extends TileEntity implements ITickableTileEntity {

    private String[] containedEncIds = {"blank"};
    private int[] containedEncLvls = {0};
    private int blockHealth = 1000;
    private int featherFallLevel = 0;
    private int fireResLevel = 0;
    private int damageTimer = 0;

    public EnchGammastoneTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public EnchGammastoneTileEntity(){
        this(ModBlocks.ENCHANTED_GAMMASTONE_BRICKS_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state,tag);

        int length = tag.getInt("containedEnchatnAmount");
        this.containedEncIds = new String[length];
        this.containedEncLvls = new int[length];

        for (int i = 0; i < length; i++){
            this.containedEncIds[i] = tag.getString("containedEnchant_"+i);
            this.containedEncLvls[i] = tag.getInt("containedEnchantLvl_"+i);
        }

        this.blockHealth = tag.getInt("blockHealth");
        this.damageTimer = tag.getInt("damageTimer");
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.save(tag);

        for (int i = 0; i < this.containedEncIds.length; i++){
            tag.putString("containedEnchant_"+i, this.containedEncIds[i]);
            tag.putInt("containedEnchantLvl_"+i, this.containedEncLvls[i]);
        }

        tag.putInt("blockHealth", this.blockHealth);
        tag.putInt("damageTimer", this.damageTimer);
        tag.putInt("containedEnchatnAmount", this.containedEncIds.length);

        return tag;
    }

    public String[] getEnchantmentIdsFromTile(){
        return this.containedEncIds;
    }
    public int[] getEnchantmentLvlsFromTile(){
        return this.containedEncLvls;
    }
    public int getBlockHealth(){return this.blockHealth;}
    public int getDamageTimer(){return this.damageTimer;}

    public void putEnchantmentIdsToTile(String[] enchIds){
        this.containedEncIds = enchIds;
    }
    public void putEnchantmentLvlsToTile(int[] enchLvls){
        this.containedEncLvls = enchLvls;
    }
    public void putBlockHealth(int hp){this.blockHealth = hp;}
    public void putDamageTimer(int time){this.damageTimer = time;}

    @Override
    public void tick() {
        //yesnt
    }
}
