package Networking.Capability;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import Networking.CodecUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;
import org.kestrel.ksgm.client.UI.Team.TeamHolder;
import org.kestrel.ksgm.common.attachments.FactionHelper;
import org.kestrel.ksgm.common.attachments.ModAttachments;


public record FactionSync(String id, String name, byte[] color) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<FactionSync> TYPE = new CustomPacketPayload.Type<>(FactionHelper.asResource("factionsync"));
    public static final StreamCodec<ByteBuf, FactionSync> STREAM_CODEC;


    public FactionSync(String id, String name, byte[] color) {
        this.id = id;
        this.color = color;
        this.name = name;

    }

    public static void serverHandle(FactionSync message, IPayloadContext c) {
        c.enqueueWork(() -> {
            Player p = c.player();
            PlayerFaction ps = (PlayerFaction) p.getData(ModAttachments.PLAYER_FACTION);
            ps.setId(message.id);
            ps.setName(message.name);
            ps.setColor(message.color);
            System.out.println("\n\nSyncing Server: " + String.valueOf(message) + "\n\n");

        });
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }


    public byte[] getColor() {
        return this.color;
    }


    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    static {
        STREAM_CODEC = CodecUtil.composite(
                ByteBufCodecs.STRING_UTF8, FactionSync::getName,
                ByteBufCodecs.STRING_UTF8, FactionSync::getID,
                ByteBufCodecs.BYTE_ARRAY, FactionSync::getColor,

                FactionSync::new
        );
    }

    public static void clientHandle(FactionSync message, IPayloadContext c) {
        c.enqueueWork(() -> {
            Player p = c.player();
            PlayerFaction ps = (PlayerFaction) p.getData(ModAttachments.PLAYER_FACTION);
            ps.setId(message.id);
            ps.setName(message.name);
            ps.setColor(message.color);
            System.out.println("\n\nSyncing Client: " + String.valueOf(message) + "\n\n");

        });
    }

}
