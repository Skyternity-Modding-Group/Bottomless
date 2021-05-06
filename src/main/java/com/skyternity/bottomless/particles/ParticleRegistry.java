package com.skyternity.bottomless.particles;

import com.skyternity.bottomless.BottomlessMain;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {
    public static DefaultParticleType GEYSER_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, new Identifier(BottomlessMain.MOD_ID, "geyser_particle"), FabricParticleTypes.simple());

    public static DefaultParticleType ENCHANT_BLASTPROT_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, new Identifier(BottomlessMain.MOD_ID, "enchant_blast_protection_particle"), FabricParticleTypes.simple());
    public static DefaultParticleType ENCHANT_FIREASPECT_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, new Identifier(BottomlessMain.MOD_ID, "enchant_fire_aspect_particle"), FabricParticleTypes.simple());
    public static DefaultParticleType ENCHANT_FLAME_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, new Identifier(BottomlessMain.MOD_ID, "enchant_flame_particle"), FabricParticleTypes.simple());
    public static DefaultParticleType ENCHANT_SHARPNESS_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, new Identifier(BottomlessMain.MOD_ID, "enchant_sharpness_particle"), FabricParticleTypes.simple());
    public static DefaultParticleType ENCHANT_SMITE_PARTICLE = Registry.register(Registry.PARTICLE_TYPE, new Identifier(BottomlessMain.MOD_ID, "enchant_smite_particle"), FabricParticleTypes.simple());

    public static void init() {
        ParticleFactoryRegistry.getInstance().register(GEYSER_PARTICLE, GeyserParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(ENCHANT_BLASTPROT_PARTICLE, EnchantmentParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ENCHANT_FIREASPECT_PARTICLE, EnchantmentParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ENCHANT_FLAME_PARTICLE, EnchantmentParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ENCHANT_SHARPNESS_PARTICLE, EnchantmentParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ENCHANT_SMITE_PARTICLE, EnchantmentParticle.Factory::new);
    }
}
