package org.kestrel.ksgm.common.DamageType;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

import static org.kestrel.ksgm.Ksgm.MODID;

public class DamageTypes {

// blunt
// slice
// puncture
// magic
// heat
// cold
// caustic
// radiation


public static ResourceKey<DamageType> BLUNT_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "blunt_damage"));
public static ResourceKey<DamageType> SLICE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "slice_damage"));
public static ResourceKey<DamageType> PUNCTURE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "puncture_damage"));
public static ResourceKey<DamageType> MAGIC_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "magic_damage"));
public static ResourceKey<DamageType> HEAT_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "heat_damage"));
public static ResourceKey<DamageType> COLD_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "cold_damage"));
public static ResourceKey<DamageType> CAUSTIC_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "caustic_damage"));
public static ResourceKey<DamageType> RADIATION_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "radiation_damage"));


}
