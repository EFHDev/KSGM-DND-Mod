package org.kestrel.ksgm.common.items.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.core.jmx.Server;
import org.kestrel.ksgm.common.Luck.LuckBasedDraw;
import org.kestrel.ksgm.common.attributes.ModdedAttributes;
import org.kestrel.ksgm.common.effects.ModEffects;

import static org.kestrel.ksgm.common.effects.ModEffects.Bleed;

public class ShortBladeBase extends TieredItem {
    public ShortBladeBase(Tier tier, Properties properties) {
        super(tier, properties
                .component(DataComponents.TOOL, SwordItem.createToolProperties())
                .attributes(SwordItem.createAttributes(tier, 3f, -2.4f))
        );
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(target instanceof ServerPlayer server)) {
            target.addEffect(new MobEffectInstance(Bleed, 200, 1, false, false));
        }

        Player victim = (ServerPlayer) target;
        Player attackerP = (ServerPlayer) attacker;

        Player winner = LuckBasedDraw.determineWinner(victim, attackerP); // Uses the players luck skills to determane if they bleed
        // from the attack. Will be replaced to check for armor, and all that jazz too to decrease/increse the chance.
        if (winner == attackerP) {
            victim.addEffect(new MobEffectInstance(Bleed, 200, 1, false, false));
        }



        return true;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double)(attackDamage), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double) attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }


}

