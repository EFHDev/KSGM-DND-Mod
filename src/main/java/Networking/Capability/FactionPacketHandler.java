package Networking.Capability;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static org.kestrel.ksgm.Ksgm.MODID;


@EventBusSubscriber(
        modid = MODID,
        bus = EventBusSubscriber.Bus.MOD
)
public class FactionPacketHandler {
    private static final String PROTOCOL_VERSION = "0.1.3";

    public FactionPacketHandler() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.commonBidirectional(FactionSync.TYPE, FactionSync.STREAM_CODEC, new DirectionalPayloadHandler<>(FactionSync::clientHandle, FactionSync::serverHandle));
    }
}
