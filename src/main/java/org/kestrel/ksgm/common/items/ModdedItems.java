package org.kestrel.ksgm.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.kestrel.ksgm.common.armor.ArmorMaterialBase;
import org.kestrel.ksgm.common.armor.ModArmorItem;
import org.kestrel.ksgm.common.attributes.ModdedAttributes;
import org.kestrel.ksgm.common.effects.ModEffects;
import org.kestrel.ksgm.common.items.base.FoodBase;
import org.kestrel.ksgm.common.items.base.SpearBase;

import static org.kestrel.ksgm.Ksgm.MODID;

public class ModdedItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<ArmorItem> WOODEN_HELMET = ITEMS.register("wooden_helmet",
            () -> new ModArmorItem(ArmorMaterialBase.wood_armor_material, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static final DeferredItem<ArmorItem> WOODEN_CHESTPLATE = ITEMS.register("wooden_chestplate",
            () -> new ArmorItem(ArmorMaterialBase.wood_armor_material, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(19))));
    public static final DeferredItem<ArmorItem> WOODEN_LEGGINGS = ITEMS.register("wooden_leggings",
            () -> new ArmorItem(ArmorMaterialBase.wood_armor_material, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(19))));
    public static final DeferredItem<ArmorItem> WOODEN_BOOTS = ITEMS.register("wooden_boots",
            () -> new ArmorItem(ArmorMaterialBase.wood_armor_material, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(19))));

    public static final DeferredItem<Item> ASTRAL_HEART = ITEMS.register("summoner_heart",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SUMMONER_BRANCH = ITEMS.register("summoner_branch",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ASTRAL_SWORD = ITEMS.register("astral_sword",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PYRO_QUARTZ_SHARD = ITEMS.register("pyro_quartz_shard",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PYRO_QUARTZ_CLUSTER = ITEMS.register("pyro_quartz_cluster",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PYRO_QUARTZ_APPLE = ITEMS.register("pyro_quartz_apple",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().nutrition(5).saturationModifier(5).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1800), 1.0f).build())));

    public static final DeferredItem<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearBase(Tiers.IRON, new Item.Properties().stacksTo(1).durability(250).attributes(SpearBase.createAttributes(Tiers.IRON, 6.0f, 3.0f).withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, ModdedAttributes.SPEAR_ATTACK_RANGE, EquipmentSlotGroup.MAINHAND))
            ));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
