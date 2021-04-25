package com.skyternity.bottomless.particles;

import com.skyternity.bottomless.entities.PorousShadestoneEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class GeyserParticle extends AnimatedParticle {
    public GeyserParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int color, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0F);
        this.field_28786 = 0.92F;
        this.scale = 0.25F;
        this.setColorAlpha(1.0F);
        this.setColor((float) BackgroundHelper.ColorMixer.getRed(color), (float)BackgroundHelper.ColorMixer.getGreen(color), (float)BackgroundHelper.ColorMixer.getBlue(color));
        this.maxAge = (int)((double)(this.scale * 12.0F) / (Math.random() * 0.8D + 0.2D));
        this.setSpriteForAge(spriteProvider);
        this.collidesWithWorld = false;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.dead) {
            this.setSpriteForAge(this.spriteProvider);
            if (this.age > this.maxAge / 2) {
                this.setColorAlpha(1.0F - ((float)this.age - (float)(this.maxAge / 2)) / (float)this.maxAge);
            }

            if (this.world.getBlockState(new BlockPos(this.x, this.y, this.z)).isAir()) {
                this.velocityY -= 0.007D;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final FabricSpriteProvider spriteProvider;

        public Factory(FabricSpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            BlockPos pos = new BlockPos(velocityX, velocityY, velocityZ);
            BlockEntity entity = world.getBlockEntity(pos);

            int color;
            if (!(entity instanceof PorousShadestoneEntity)) {
                color = 0;
            } else {
                String potionId = ((PorousShadestoneEntity) entity).potion;
                color = PotionUtil.getColor(Registry.POTION.get(new Identifier(potionId)));
            }

            return createParticle(
                    parameters, world, x, y, z, 0.0D, 0.8D, 0.0D,
                    color
            );
        }

        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int color) {
            return new GeyserParticle(
                    world, x, y, z, velocityX, velocityY, velocityZ,
                    color,
                    this.spriteProvider
            );
        }
    }
}
