package org.kestrel.ksgm.common.attachments;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;
import org.kestrel.ksgm.Server.Factions.Faction;
import org.kestrel.ksgm.Server.Factions.FactionManager;
import org.kestrel.ksgm.Server.Factions.FactionMember;

import java.nio.ByteBuffer;
import java.util.Objects;

import static org.kestrel.ksgm.Ksgm.MODID;

@EventBusSubscriber(modid = MODID)
public class FactionHelper {
    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath("ksgm", path);
    }


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {

        Player var2 = event.getEntity();
        if (var2 instanceof ServerPlayer serverPlayer) {
            PlayerFaction pf = (PlayerFaction) serverPlayer.getData(ModAttachments.PLAYER_FACTION);
            pf.tick(serverPlayer);
            if (pf == null) {
                serverPlayer.setData(ModAttachments.PLAYER_FACTION, new PlayerFaction());
            }
            if (serverPlayer.getName().getString().equals("Kestrel")) {
                if (Objects.equals(pf.getId(), "wilderness")) {
                    FactionManager.foundFaction("sizzyville", serverPlayer);
                }
            } else {
                if (pf.isWild()) {
                    FactionManager.joinFaction("sizzyville", serverPlayer);
                }
            }

        }

    }

    public static PlayerFaction get(ServerPlayer p) {
        return p.getData(ModAttachments.PLAYER_FACTION);
    }

    public static FactionMember getFactionMember(ServerPlayer p) {
        return p.getData(ModAttachments.PLAYER_FACTION).getFaction().member(p);
    }


    public static byte[] floatToByteArray(float[] floatArray) {
        ByteBuffer buffer = ByteBuffer.allocate(floatArray.length * 4);
        buffer.asFloatBuffer().put(floatArray);
        return buffer.array();
    }

    public static byte[] intArrayToByteArray(int[] intArray) {
        ByteBuffer buffer = ByteBuffer.allocate(intArray.length * 4);
        for (int value : intArray) {
            buffer.putInt(value);
        }
        return buffer.array();
    }

    public static int[] byteArrayToIntArray(byte[] byteArray) {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        int[] intArray = new int[byteArray.length / 4];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = buffer.getInt();
        }
        return intArray;

    }

    public static float[] byteArrayToFloat(byte[] byteArray) {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        float[] floatArray = new float[byteArray.length / 4];
        buffer.asFloatBuffer().get(floatArray);
        return floatArray;
    }

}
