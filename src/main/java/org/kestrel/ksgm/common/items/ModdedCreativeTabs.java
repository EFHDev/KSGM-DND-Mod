package org.kestrel.ksgm.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;
import static org.kestrel.ksgm.Ksgm.MODID;

public class ModdedCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> KSGMTAB = CREATIVE_MODE_TAB.register("bismuth_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModdedItems.PYRO_QUARTZ_APPLE.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModdedItems.PYRO_QUARTZ_APPLE);
                        output.accept(ModdedItems.ASTRAL_HEART);

                        output.accept(ModdedItems.SUMMONER_BRANCH);
                        output.accept(ModdedItems.WOODEN_BOOTS);
                        output.accept(ModdedItems.WOODEN_LEGGINGS);
                        output.accept(ModdedItems.WOODEN_CHESTPLATE);
                        output.accept(ModdedItems.WOODEN_HELMET);
                        output.accept(ModdedItems.WOODEN_SPEAR);
                        output.accept(ModdedItems.ASTRAL_SWORD);


                        output.accept(ModdedItems.PYRO_QUARTZ_CLUSTER);
                        output.accept(ModdedItems.PYRO_QUARTZ_SHARD);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}

