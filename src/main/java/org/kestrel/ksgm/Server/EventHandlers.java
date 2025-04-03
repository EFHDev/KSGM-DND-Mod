package org.kestrel.ksgm.Server;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;
import org.kestrel.ksgm.Server.Factions.Faction;
import org.kestrel.ksgm.common.attachments.FactionHelper;
import org.kestrel.ksgm.common.attachments.ModAttachments;

import static org.kestrel.ksgm.Ksgm.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = {Dist.DEDICATED_SERVER})
public class EventHandlers {

    public EventHandlers() {

    }

    @SubscribeEvent
    public static void onChatMessage(ServerChatEvent event) {
        if (event.getMessage().getString().startsWith("@F")) {
            ServerPlayer p = event.getPlayer();
            PlayerFaction pf = FactionHelper.get(p);
            event.setCanceled(true);
            if (pf.isWild()) {
                p.sendSystemMessage(Component.translatable("ksgm.faction.not_in_faction"));
                return;
            }
            Faction f = pf.getFaction();
            f.sendChatMessageToAll(p, event.getMessage().getString().replaceFirst("@F ", ""));


        }

    }

}
