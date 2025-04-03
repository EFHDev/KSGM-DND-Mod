package org.kestrel.ksgm.common.effects;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.kestrel.ksgm.common.DamageType.DamageTypes;
import org.kestrel.ksgm.common.attributes.ModdedAttributes;

import java.util.Objects;

import static org.kestrel.ksgm.Ksgm.MODID;
import static org.kestrel.ksgm.common.DamageType.DamageTypes.BLEED;

public class Bleed extends MobEffect {

    private int tickCount;

    public Bleed(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickTimer, int amplifier) {
        tickCount++;
        switch (amplifier){
            case 1: return tickTimer % 85 == 0;
            case 2: return tickTimer % 75 == 0;
            case 3: return tickTimer % 65 == 0;
            case 4: return tickTimer % 55 == 0;
            case 5: return tickTimer % 45 == 0;
            default: return tickTimer % 40 == 0;

        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        if (!level.isClientSide()) {


      Player player = (ServerPlayer) livingEntity;
      player.displayClientMessage(Component.literal("Timer: " + tickCount), true);
      tickCount = 0;
            Holder<DamageType> bleedHolder =
                    level
                            .registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(BLEED);

            player.hurt(new DamageSource(bleedHolder), 2);
        }        return super.applyEffectTick(livingEntity, amplifier);
    }
}