package com.skyternity.bottomless.blocks.enchanted_gammastone;

import com.skyternity.bottomless.blocks.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchGammastoneTileEntity extends BlockEntity implements BlockEntityTicker{

    private String[] containedEncIds = {"blank"};
    private int[] containedEncLvls = {0};
    private int blockHealth = 1000;
    private int featherFallLevel = 0;
    private int fireResLevel = 0;
    private int damageTimer = 0;

    public EnchGammastoneTileEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.ENCH_GAMMASTONE_TILEENTITY, pos, state);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);

        for (int i = 0; i < this.containedEncIds.length; i++){
            tag.putString("containedEnchant_"+i, this.containedEncIds[i]);
            tag.putInt("containedEnchantLvl_"+i, this.containedEncLvls[i]);
        }

        tag.putInt("blockHealth", this.blockHealth);
        tag.putInt("damageTimer", this.damageTimer);
        tag.putInt("containedEnchatnAmount", this.containedEncIds.length);

        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

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
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        System.out.println("ASDASDASD");
    }
}
