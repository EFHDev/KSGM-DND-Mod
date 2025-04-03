package org.kestrel.ksgm.common.Events.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import org.kestrel.ksgm.Server.Factions.Faction;

public abstract class FactionCreated extends Event {
    private final Faction faction;
    private final ServerPlayer leader;


    public FactionCreated(Faction faction, ServerPlayer leader) {

        this.faction = faction;
        this.leader = leader;
    }


    public ServerPlayer getLeader() {
        return leader;
    }

    public Faction getFaction() {
        return faction;
    }

    public static class Pre extends FactionCreated {
        public Pre(Faction faction, ServerPlayer recruit) {
            super(faction, recruit);
        }
    }
}
