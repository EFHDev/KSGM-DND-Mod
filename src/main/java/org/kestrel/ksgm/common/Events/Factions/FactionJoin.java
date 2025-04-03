package org.kestrel.ksgm.common.Events.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;
import org.apache.logging.log4j.core.jmx.Server;
import org.kestrel.ksgm.Server.Factions.Faction;

public abstract class FactionJoin extends Event {
    private final Faction faction;
    private final ServerPlayer recruit;


    public FactionJoin(Faction faction, ServerPlayer recruit) {
        this.faction = faction;
        this.recruit = recruit;
    }


    public ServerPlayer getRecruit() {
        return recruit;
    }

    public Faction getFaction() {
        return faction;
    }

    public static class Pre extends FactionJoin {
        public Pre(Faction faction, ServerPlayer recruit) {
            super(faction, recruit);
        }
    }
}
