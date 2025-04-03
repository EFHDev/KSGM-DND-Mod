package org.kestrel.ksgm.common.attachments;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.kestrel.ksgm.Ksgm;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Ksgm.MODID);
    public static final Supplier<AttachmentType<PlayerFaction>> PLAYER_FACTION = ATTACHMENTS.register("player_faction", () -> {
        return AttachmentType.serializable(PlayerFaction::new).copyOnDeath().build();
    });
}
