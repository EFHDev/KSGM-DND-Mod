package org.kestrel.ksgm.common.classes;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;

public class PlayerClass implements IPlayerClass {
    private final String name;
    private final int healthModifier;
    private List<ItemLike> allowed_weapons;
    private int level = 1;
    private int experience = 0;


    private boolean needSync = true;


    public PlayerClass(String name, int healthModifier, List<ItemLike> allowedWeapons) {
        this.name = name;
        this.healthModifier = healthModifier;
        this.allowed_weapons = allowedWeapons;
    }

    public void AddAllowedWeapon(ItemLike item){
        if (item == null)
            return;
        if (!this.allowed_weapons.contains(item))
        this.allowed_weapons.add(item);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }
    @Override
    public void LevelUp(int level) {
        this.level += 1;
        this.setExperience(0);
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHealthModifier() {
        return this.healthModifier;
    }

    @Override
    public List<ItemLike> getAllowed_weapons() {
        return this.allowed_weapons;
    }
}
