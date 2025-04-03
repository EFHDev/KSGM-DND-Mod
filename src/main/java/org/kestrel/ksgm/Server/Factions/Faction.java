package org.kestrel.ksgm.Server.Factions;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.core.jmx.Server;
import org.kestrel.ksgm.common.Events.Factions.FactionInvite;
import org.kestrel.ksgm.common.attachments.ModAttachments;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Faction {
    private String name;
    private String description;
    private String factionID;
    private int[] factionColor = new int[]{50, 205, 50};
    private UUID leader;
    private List<UUID> officers = new ArrayList<>();
    private List<FactionMember> members = new ArrayList<>();
    private List<Faction> enemies = new ArrayList<>();
    private List<Faction> allies = new ArrayList<>();
    private List<?> cities = new ArrayList<>();
    private boolean isPublic = false;
    private List<UUID> invites = new ArrayList<>();
    private static final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
//    private final PlayerTeam team = server.getScoreboard().addPlayerTeam(this.name);


    public enum Rank {
        LEADER,
        OFFICER,
        MEMBER
    }

    public Faction(String name, UUID leader, String factionID) {
        this.name = name;
        this.leader = leader;
        this.factionID = factionID;
        this.members.add(new FactionMember(leader, this.factionID, server.getPlayerList().getPlayer(leader).getName().getString()));
    }


    public UUID getLeader() {
        return leader;
    }

    public Rank getRank(ServerPlayer player) {
        if (isLeader(player)) {
            return Rank.LEADER;
        } else if (isOfficer(player)) {
            return Rank.OFFICER;
        } else {
            return Rank.MEMBER;
        }
    }

    public boolean isLeadership(ServerPlayer player) {
        Rank playerRank = getRank(player);
        return playerRank == Rank.LEADER || playerRank == Rank.OFFICER;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public List<FactionMember> getMembers() {
        return members;
    }

    public FactionMember member(UUID uuid) {
        return members.stream().filter(member -> member.getUUID().equals(uuid)).findFirst().orElse(null);
    }

    public FactionMember member(ServerPlayer player) {
        String uuid = player.getStringUUID();
        return members.stream().filter(member -> member.getUUID().equals(uuid)).findFirst().orElse(null);
    }


    // Adds all the faction members to a team
//    public void AddMembersToTeam() {
//        for (FactionMember member : members) {
//            if (member.isOnline()) {
//                ServerPlayer player = member.getPlayer();
//                if (player != null && player.getTeam() != team) {
//                    server.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
//                }
//            }
//        }
//    }

//    public void MemberCameOnline(FactionMember m) {
//        if (m.isOnline()) { //Sanity Check
//            ServerPlayer player = m.getPlayer();
//            if (player != null && player.getTeam() != team) {
//                server.getScoreboard().addPlayerToTeam(player.getScoreboardName(), team);
//            }
//        }
//    }

    public FactionMember memberByName(String name) {
        return members.stream().filter(member -> member.getName().equals(name)).findFirst().orElse(null);
    }

    public FactionMember getMember(ServerPlayer player) {
        return memberByName(player.getName().getString());
    }

    public String getFactionID() {
        return this.factionID;
    }

    public boolean isInvited(UUID player) {
        if (!invites.isEmpty()) {
            for (UUID invite : invites) {
                if (invite.equals(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean invitePlayer(ServerPlayer p, ServerPlayer self) {
        if (invites.contains(p.getUUID())) {
            self.sendSystemMessage(Component.translatable("ksgm.faction.already_invited", p.getName().getString()), true);
            return false;
        }
        invites.add(p.getUUID());
        if (p.getData(ModAttachments.PLAYER_FACTION).receiveInvite(this, self, p)) {
            NeoForge.EVENT_BUS.post(new FactionInvite.Pre(this, p, self));

        } else {
            self.sendSystemMessage(Component.translatable("ksgm.faction.invite_failed", p.getName().getString()), true);
            invites.remove(p.getUUID());
            return false;
        }
        ;
        return true;
    }


    public String getMemberPrefix(FactionMember p) {
        if (!p.isOnline()) {
            return "";
        }
        if (isLeader(p.getPlayer())) {
            return "§6§lLeader§r ";
        } else if (isOfficer(p.getPlayer())) {
            return "§1§lOfficer§r ";
        } else {
            return "§n§2Lieutenant§r ";
        }
    }

    public void sendChatMessageToAll(ServerPlayer sender, String message) {
        members.forEach(member -> {
            if (member.isOnline()) {
                StringBuilder Builder = new StringBuilder();
                Builder.append(getMemberPrefix(memberByName(sender.getName().getString())));
                Builder.append(sender.getName().getString());
                Builder.append("§r");
                String formattedName = Builder.toString();

                member.getPlayer().sendSystemMessage(Component.literal(
                        String.format("§l§a\uD83D\uDDE8§r%s §l§7»§r %s ✧ %s", " §a§o" + name + "§r", formattedName, message)
                ));
            }
        });
    }

    public void sendMessageToAll(ServerPlayer sender, Component message) {
        members.forEach(member -> {
            if (member.isOnline()) {
                StringBuilder Builder = new StringBuilder();
                Builder.append(getMemberPrefix(memberByName(sender.getName().getString())));
                Builder.append(sender.getName().getString());
                Builder.append("§r");
                String formattedName = Builder.toString();

                member.getPlayer().sendSystemMessage(Component.literal(
                        String.format("§8[§l§cK§fF§r§8]§r §l§8»§r %s ✧ %s", formattedName, message.getString())
                ));
            }
        });
        return;
    }


    public boolean revokeInvite(ServerPlayer p, ServerPlayer self) {
        if (!invites.contains(p.getUUID())) {
            self.sendSystemMessage(Component.translatable("ksgm.faction.not_invited", p.getName().getString()), true);
            return false;
        }
        invites.remove(p.getUUID());
        p.getData(ModAttachments.PLAYER_FACTION).revokeInvite(this, self, p);
        return true;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void addMember(FactionMember member) {
        members.add(member);
    }

    public void removeMember(FactionMember member) {
        members.remove(member);
    }

    public void addOfficer(UUID officer) {
        officers.add(officer);
    }

    public void addOfficer(FactionMember officer) {
        officers.add(officer.getUUID());
    }

    public boolean isOfficer(UUID member) {
        return officers.contains(member);
    }

    public boolean isOfficer(ServerPlayer member) {
        return officers.contains(member.getUUID());
    }

    public boolean isLeader(ServerPlayer member) {
        return leader.equals(member.getUUID());
    }


    public void setName(String name, UUID changee) {
        if (officers.contains(changee)) {
            this.name = name;
            members.forEach(member -> {
                if (member.isOnline()) {
                    member.getPlayer().sendSystemMessage(Component.translatable("ksgm.faction.name_change", name));
                }
            });
        }
    }

    public int[] getFactionColor() {
        return factionColor;
    }

    public void setFactionColor(int[] factionColor) {
        this.factionColor = factionColor;
    }

    public void setFactionID(String factionID) {
        this.factionID = factionID;
    }

    public void togglePublic() {
        this.isPublic = !this.isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

//    public PlayerTeam getTeam() {
//        return team;
//    }
}
