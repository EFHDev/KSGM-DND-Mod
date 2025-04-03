package org.kestrel.ksgm.Server.Factions.Attachment;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import Networking.Capability.FactionSync;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.kestrel.ksgm.API.Faction.IFaction;
import org.kestrel.ksgm.Server.Factions.Faction;
import org.kestrel.ksgm.Server.Factions.FactionManager;
import org.kestrel.ksgm.Server.Factions.FactionMember;
import org.kestrel.ksgm.common.attachments.FactionHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerFaction implements INBTSerializable<CompoundTag>, IFaction {

    private String name;
    private String id;
    private int[] color = new int[]{50, 205, 50};
    public boolean needSync = false;
    public boolean first = true;
    private String[] invites = new String[5];
//    private Map<String, Integer> relations = new HashMap<>();


    enum FriendOrFoe {
        FRIEND,
        FOE
    }

    public void updateFactionData(Player player) {
        PacketDistributor.sendToAllPlayers(new FactionSync(this.id, this.name, FactionHelper.intArrayToByteArray(this.color)));
    }


    public FriendOrFoe isFriendOrFoe(ServerPlayer p) {
        return FactionManager.getFaction(this.id).getMember(p) == null ? FriendOrFoe.FOE : FriendOrFoe.FRIEND;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("faction_name", this.name);
        nbt.putString("faction_id", this.id);
        nbt.putIntArray("faction_color", this.color);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.name = nbt.getString("faction_name");
        this.id = nbt.getString("faction_id");
        this.color = nbt.getIntArray("faction_color");
    }

    public int[] getColor() {
        return this.color;
    }


    public void UpdateFaction(Faction f) {
        this.name = f.getName();
        this.id = f.getFactionID();
        this.color = f.getFactionColor();
        this.needSync = true;
    }

    public void setColor(int[] color) {
        this.color = color;
        this.needSync = true;
    }

    public void setColor(byte[] color) {
        this.color = FactionHelper.byteArrayToIntArray(color);
        this.needSync = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.needSync = true;
    }

    public Faction getFaction() {
        if (Objects.equals(this.name, "Wilderness")) {
            return null;
        }
        return FactionManager.getFaction(id);
    }

    public boolean isWild() {
        return Objects.equals(this.id, "wilderness");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.needSync = true;

    }

    public void tick(Player p) {

        if (this.first) {
            this.id = "wilderness";
            this.color = new int[]{50, 205, 50};
            this.name = "Wilderness";
            this.needSync = true;
            this.first = false;
        }
//        p.displayClientMessage(Component.literal("Faction:" + this.id + " " + this.name + " Invites:" + this.invites.length), true);


        if (this.needSync) {
            this.updateFactionData(p);
            this.needSync = false;
        }
    }

    public boolean receiveInvite(Faction f, ServerPlayer self, ServerPlayer sender) {
        if (self.level().isClientSide()) return false;
        if (this.invites.length < 5) {
            for (int i = 0; i < this.invites.length; i++) {
                if (this.invites[i] == null) {
                    this.invites[i] = f.getName();
                    self.sendSystemMessage(Component.translatable("ksgm.faction.invite_received", sender.getDisplayName(), f.getName()));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean revokeInvite(Faction f, ServerPlayer self, ServerPlayer sender) {
        for (int i = 0; i < this.invites.length; i++) {
            if (this.invites[i].equals(f.getName())) {
                this.invites[i] = null;
                sender.sendSystemMessage(Component.translatable("ksgm.faction.invite_revoked", sender.getDisplayName(), f.getName()));
                return true;
            }
        }

        return false;
    }

    public void acceptInvite(String factionName, ServerPlayer self) {
        Faction f = FactionManager.getFactionByName(name);
        PlayerFaction pf = FactionHelper.get(self);
        if (f == null) {
            self.sendSystemMessage(Component.translatable("ksgm.faction.invite_invalid"), true);
            return;
        }
        if (!(f.isInvited(self.getUUID()) && Arrays.asList(pf.getInvites()).contains(name))) {
            self.sendSystemMessage(Component.translatable("ksgm.faction.invite_invalid"), true);
            return;
        }

        FactionManager.joinFaction(name, self);
    }

    public String[] getInvites() {
        return invites;
    }

    public void setInvites(String[] invites) {
        this.invites = invites;
    }
}
