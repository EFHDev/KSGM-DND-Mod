package org.kestrel.ksgm.Server.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.server.ServerLifecycleEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FactionMember {
    private final UUID player;
    private final String name;
    private final String joinedFaction;
    private final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();


    public FactionMember(UUID player, String factionID, String name) {
        this.player = player;
        this.name = name;
        this.joinedFaction = factionID;
    }

    public UUID getUUID() {
        return player;
    }

    public ServerPlayer getPlayer() {
        if (!isOnline()) {
            return null;
        }
        assert server != null;
        return server.getPlayerList().getPlayer(player);
    }

    public boolean isOnline() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player.getUUID().equals(this.player)) {
                return true;
            }
        }
        return false;
    }
    

    public String getFactionID() {
        return joinedFaction;
    }

    public boolean isOfficer() {
        return FactionManager.getFaction(joinedFaction).isOfficer(player);
    }

    public String getName() {
        return name;
    }
}
