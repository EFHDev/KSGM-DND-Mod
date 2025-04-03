package org.kestrel.ksgm.common.Events.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;
import org.kestrel.ksgm.Server.Factions.Faction;
import org.kestrel.ksgm.common.attachments.ModAttachments;

public abstract class FactionInvite extends Event {
    private final ServerPlayer recruit;
    private final ServerPlayer inviter;
    private Faction faction;
    private final boolean isInFaction;


    public FactionInvite(Faction faction, ServerPlayer recruit, ServerPlayer inviter) {
        this.recruit = recruit;
        this.inviter = inviter;
        this.faction = inviter.getData(ModAttachments.PLAYER_FACTION).getFaction();
        this.isInFaction = recruit.getData(ModAttachments.PLAYER_FACTION).isWild();
    }


    public ServerPlayer getRecruit() {
        return recruit;
    }

    public Faction getFaction() {
        return faction;
    }

    public static class Pre extends FactionInvite {
        public Pre(Faction faction, ServerPlayer recruit, ServerPlayer inviter) {
            super(faction, recruit, inviter);
        }
    }
}
