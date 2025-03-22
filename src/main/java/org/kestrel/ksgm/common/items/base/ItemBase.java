package org.kestrel.ksgm.common.items.base;


import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBase extends Item {

    public String name;

    public ItemBase(Properties properties) {
        super(properties);
    }



    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @NotNull TooltipContext p_339594_, @NotNull List<Component> p_41423_, @NotNull TooltipFlag p_41424_) {
    }
}
