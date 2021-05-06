package com.skyternity.bottomless.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class EnchantmentParticle extends AnimatedParticle {

    public EnchantmentParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, spriteProvider, 1.0f);
        this.velocityX = g;
        this.velocityY = h;
        this.velocityZ = i;
        this.scale *= 0.75F;
        this.maxAge = 60 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);
        this.gravityStrength = -0.01f;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new EnchantmentParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }

}
