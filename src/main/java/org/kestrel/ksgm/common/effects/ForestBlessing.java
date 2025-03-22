package org.kestrel.ksgm.common.effects;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.common.Mod;
import org.kestrel.ksgm.common.attributes.ModdedAttributes;

import java.util.Objects;

public class ForestBlessing extends MobEffect {
    private boolean inForest = false; // Track if the player is currently in a forest biome

    public ForestBlessing(MobEffectCategory category, int color) {
        super(category, color);
    }

    private ResourceLocation id = ResourceLocation.fromNamespaceAndPath("yourmodid", "my_modifier");
    // The modifier itself.
    private AttributeModifier modifier = new AttributeModifier(
            id,
            5.05,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Holder<Biome> biome = livingEntity.level().getBiome(livingEntity.blockPosition());
        Player player = (Player) livingEntity;
        player.displayClientMessage(Component.literal("currentSpeed: " + livingEntity.getSpeed() + " Biome: " + biome.getRegisteredName()), true);

        boolean isCurrentlyInForest = biome.getRegisteredName().toLowerCase().contains("forest");

        if (isCurrentlyInForest != inForest) {
            if (isCurrentlyInForest) {
                if (livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).hasModifier(ResourceLocation.fromNamespaceAndPath("ksgm", "forests_blessing"))) {
                    return false;
                }
                Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED)).addTransientModifier(ModdedAttributes.FORESTS_BLESSING);
            } else {
                livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).removeModifier(ModdedAttributes.FORESTS_BLESSING);
                System.out.println("Left forest biome, speed decreased.");
            }
            inForest = isCurrentlyInForest; // Update the tracking boolean
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }
}