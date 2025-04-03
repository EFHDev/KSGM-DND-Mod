package org.kestrel.ksgm.client.UI.Team;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamHolder {
    private static final Map<UUID, int[]> teammates = new HashMap<>();


    public static void addPlayertoTeam(UUID player, int[] faction) {
        teammates.put(player, faction);
    }

    public static void removePlayerFromTeam(UUID player) {
        teammates.remove(player);
    }

    public static int[] getFaction(UUID player) {
        return teammates.get(player);
    }


    public static boolean isFriend(UUID player) {
        return teammates.containsKey(player);
    }
}
