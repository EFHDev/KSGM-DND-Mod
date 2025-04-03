package org.kestrel.ksgm.common.DamageType;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

import static org.kestrel.ksgm.Ksgm.MODID;

public class DamageTypes {



    public static ResourceKey<DamageType> BLUNT_DAMAGE;
    public static ResourceKey<DamageType> SLICE_DAMAGE;
    public static ResourceKey<DamageType> PUNCTURE_DAMAGE;
    public static ResourceKey<DamageType> MAGIC_DAMAGE;
    public static ResourceKey<DamageType> HEAT_DAMAGE;
    public static ResourceKey<DamageType> COLD_DAMAGE;
    public static ResourceKey<DamageType> CAUSTIC_DAMAGE;
    public static ResourceKey<DamageType> RADIATION_DAMAGE;
    public static ResourceKey<DamageType> BLEED;

    public static void registerDamageTypes() {
        BLUNT_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "blunt_damage"));
        SLICE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "slice_damage"));
        PUNCTURE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "puncture_damage"));
        MAGIC_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "magic_damage"));
        HEAT_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "heat_damage"));
        COLD_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "cold_damage"));
        CAUSTIC_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "caustic_damage"));
        RADIATION_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "radiation_damage"));
        BLEED = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "bleed_damage"));
    }

}
