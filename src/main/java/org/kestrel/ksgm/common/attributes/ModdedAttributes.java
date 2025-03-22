package org.kestrel.ksgm.common.attributes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.kestrel.ksgm.Ksgm.MODID;

public class ModdedAttributes {


    public static final ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MODID, "forests_blessing");
    public static final ResourceLocation SPEARRNG = ResourceLocation.fromNamespaceAndPath(MODID, "spear_attack_range");

    public static final AttributeModifier FORESTS_BLESSING = new AttributeModifier(
            id,
            5.0,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );

    public static final AttributeModifier SPEAR_ATTACK_RANGE = new AttributeModifier(
            SPEARRNG,
            -2,
            AttributeModifier.Operation.ADD_VALUE
    );
}
