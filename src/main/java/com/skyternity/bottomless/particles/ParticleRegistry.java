package com.skyternity.bottomless.particles;

import com.skyternity.bottomless.BottomlessMain;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {
    public static DefaultParticleType GEYSER_PARTICLE = Registry.register(
            Registry.PARTICLE_TYPE,
            new Identifier(BottomlessMain.MOD_ID, "geyser_particle"),
            FabricParticleTypes.simple()
    );

    public static void init() {
        ParticleFactoryRegistry.getInstance().register(GEYSER_PARTICLE, GeyserParticle.Factory::new);
    }
}
