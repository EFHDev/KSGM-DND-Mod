package org.kestrel.ksgm.common.items.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FoodBase extends Item {
    public static FoodProperties FOOD_PROPERTIES = null;

    public FoodBase(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack Stack, TooltipContext Context, List<Component> List, TooltipFlag Flag) {
        List.add(Component.literal("Ive been modified"));
        super.appendHoverText(Stack, Context, List, Flag);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (level.isClientSide) {
        }
        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.has(DataComponents.FOOD)) {
            super.finishUsingItem(stack, level, consumer);
        } else {
            Player player = consumer instanceof Player ? (Player) consumer : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }
        if (stack.isEmpty()) {
            return containerStack;
        } else {
            if (consumer instanceof Player player && !player.getAbilities().instabuild) {
                if (!player.getInventory().add(containerStack)) {
                    player.drop(containerStack, false);
                }
            }
            return stack;
        }
    }


}