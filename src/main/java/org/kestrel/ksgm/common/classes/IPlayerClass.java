package org.kestrel.ksgm.common.classes;


import net.minecraft.world.level.ItemLike;

import java.util.List;

public interface IPlayerClass {
    void AddAllowedWeapon(ItemLike item);
    int getLevel();
    void setLevel(int level);
    void LevelUp(int level);
    int getExperience();
    void setExperience(int experience);

    String getName();
    int getHealthModifier();
    List<ItemLike> getAllowed_weapons();
}