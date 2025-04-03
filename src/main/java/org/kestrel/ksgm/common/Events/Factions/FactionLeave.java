package org.kestrel.ksgm.common.Events.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;
import org.kestrel.ksgm.Server.Factions.Faction;

public abstract class FactionLeave extends Event {
    private final Faction faction;
    private final ServerPlayer recruit;


    public FactionLeave(Faction faction, ServerPlayer recruit) {
        this.faction = faction;
        this.recruit = recruit;
    }


    public ServerPlayer getRecruit() {
        return recruit;
    }

    public Faction getFaction() {
        return faction;
    }

    public static class Pre extends FactionLeave {
        public Pre(Faction faction, ServerPlayer recruit) {
            super(faction, recruit);
        }
    }
}
