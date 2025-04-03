package org.kestrel.ksgm.Server.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;
import org.kestrel.ksgm.common.Events.Factions.FactionCreated;
import org.kestrel.ksgm.common.Events.Factions.FactionJoin;
import org.kestrel.ksgm.common.attachments.ModAttachments;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FactionManager {
    private static final Map<String, Faction> factions = new HashMap<>();
    private static final Map<UUID, String> playerFactions = new HashMap<>();
    private static final Map<String, Faction> factionsByName = new HashMap<>();

    public static Faction getFaction(String id) {
        return factions.get(id);
    }

    public static Faction getFactionByName(String name) {
        if (factionsByName.get(name) == null) {
            return null;
        }
        return factionsByName.get(name);
    }

    public static Faction foundFaction(String name, ServerPlayer player) {
        if (playerFactions.containsKey(player.getUUID())) {
            player.sendSystemMessage(Component.translatable("ksgm.faction.already_in_faction"), true);
            return null;
        }
        if (factionsByName.containsKey(name)) {
            player.sendSystemMessage(Component.translatable("ksgm.faction.name_taken", name));
            return null;
        }
        PlayerFaction pf = (PlayerFaction) player.getData(ModAttachments.PLAYER_FACTION);

        System.out.print("Founding faction " + name + " " + player.getName().getString());
        String id = UUID.randomUUID().toString();
        Faction faction = new Faction(name, player.getUUID(), id);
        factions.put(id, faction);
        playerFactions.put(player.getUUID(), name);
        factionsByName.put(name, faction);
        player.level().getServer().getPlayerList().broadcastSystemMessage(Component.translatable("ksgm.faction.founded", player.getDisplayName(), name), true);
        pf.UpdateFaction(faction);
        //NeoForge.EVENT_BUS.post(new FactionCreated.Pre(faction, player));
        return faction;
    }

    public static Faction joinFaction(String name, ServerPlayer player) {
        Faction faction = getFactionByName(name);
        System.out.print("Joining faction " + name + " " + player.getName().getString());
        faction.addMember(new FactionMember(player.getUUID(), faction.getFactionID(), player.getName().getString()));
        PlayerFaction pf = (PlayerFaction) player.getData(ModAttachments.PLAYER_FACTION);
        pf.UpdateFaction(faction);
//        NeoForge.EVENT_BUS.post(new FactionJoin.Pre(faction, player));
        faction.sendMessageToAll(player, Component.translatable("ksgm.faction.player_joined", player.getDisplayName().getString()));
        return faction;
    }

    public static Faction leaveFaction(ServerPlayer player) {
//        Faction faction = new Faction(name, player.getUUID());
//        String id = UUID.randomUUID().toString();
//        factions.put(id, faction);
//        playerFactions.put(player.getUUID(), id);
//        player.level().getServer().getPlayerList().broadcastSystemMessage(Component.translatable("ksgm.faction.founded", player.getDisplayName(), name), true);
        return null;
    }

}
