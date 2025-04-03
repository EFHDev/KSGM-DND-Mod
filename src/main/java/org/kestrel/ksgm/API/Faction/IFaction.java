package org.kestrel.ksgm.API.Faction;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */
import org.kestrel.ksgm.Server.Factions.Faction;

public interface IFaction {


    int[] getColor();
    void setColor(int[] color);
    String getId();
    void setId(String id);
    String getName();
    void setName(String name);

    void UpdateFaction (Faction f);
}
