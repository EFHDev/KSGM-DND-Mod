package org.kestrel.ksgm.Server.Commands;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;
import org.kestrel.ksgm.Server.Factions.Faction;
import org.kestrel.ksgm.Server.Factions.FactionManager;
import org.kestrel.ksgm.common.CenteredText;
import org.kestrel.ksgm.common.attachments.FactionHelper;
import org.kestrel.ksgm.common.attachments.ModAttachments;

import java.util.Arrays;
import java.util.Objects;

import static org.kestrel.ksgm.Ksgm.MODID;

@EventBusSubscriber(modid = MODID)
public class ServerCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("kf")
                .then(Commands.literal("establish")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(ctx -> establish(ctx, StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("invite")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx ->
                                        invite(ctx, EntityArgument.getPlayer(ctx, "player")))))
                .then(Commands.literal("join")
                        .executes(ctx -> {
                            ServerPlayer p = ctx.getSource().getPlayerOrException();
                            PlayerFaction pf = FactionHelper.get(p);

                            StringBuilder sb = new StringBuilder();
                            sb.append(CenteredText.getCenteredMessage(Component.translatable("ksgm.faction.join_no_arg").getString()));
                            sb.append(CenteredText.getCenteredMessage(Component.translatable("ksgm.faction.join_no_arg_2").getString()));
                            Arrays.stream(pf.getInvites()).toList().forEach(
                                    invite -> {
                                        if (invite != null)
                                            sb.append(CenteredText.getCenteredMessage(String.format("§e%s", invite)));
                                    }
                            );
                            p.sendSystemMessage(Component.literal(sb.toString()));
                            return 1;
                        }))
                .then(Commands.literal("leave")
                        .executes(ctx -> {
                            ctx.getSource().sendSystemMessage(Component.literal("not implemented"));
                            return 1;
                        }))
                .then(Commands.literal("kick")
                        .executes(ctx -> {
                            ctx.getSource().sendSystemMessage(Component.literal("not implemented"));
                            return 1;
                        }))
                .then(Commands.literal("promote")
                        .executes(ctx -> {
                            ctx.getSource().sendSystemMessage(Component.literal("not implemented"));
                            return 1;
                        }))
                .then(Commands.literal("demote")
                        .executes(ctx -> {
                            ctx.getSource().sendSystemMessage(Component.literal("not implemented"));
                            return 1;
                        }))
                .then(Commands.literal("testmessages")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            PlayerFaction pf = FactionHelper.get(player);
                            pf.getFaction().sendMessageToAll(player, Component.literal("I dropped my burrito :("));
                            return 1;
                        }))
                .then(Commands.literal("disband")
                        .executes(ctx -> {
                            ctx.getSource().sendSystemMessage(Component.literal("not implemented"));

                            return 1;
                        })));
    }


    public static int establish(CommandContext<CommandSourceStack> ctx, String name) throws CommandSyntaxException {
        FactionManager.foundFaction(name, ctx.getSource().getPlayerOrException());

        return 1;
    }

    public static int invite(CommandContext<CommandSourceStack> ctx, ServerPlayer target) throws CommandSyntaxException {
        PlayerFaction inviter = (PlayerFaction) ctx.getSource().getPlayerOrException().getData(ModAttachments.PLAYER_FACTION);
        if (Objects.equals(inviter.getName(), "Wilderness") || Objects.equals(inviter.getId(), "wilderness")) {
            ctx.getSource().sendFailure(Component.translatable("ksgm.faction.invite_not_allowed"));
        }


        PlayerFaction invitee = (PlayerFaction) target.getData(ModAttachments.PLAYER_FACTION);
        inviter.getFaction().invitePlayer(ctx.getSource().getPlayerOrException(), target);

        if (inviter.getFaction().invitePlayer(target, ctx.getSource().getPlayerOrException())) {
            ctx.getSource().sendSuccess(() -> Component.translatable("ksgm.faction.invite_sent", target.getDisplayName()), true);
        }
        ;
        return 1;
    }

    public static void AcceptInvite(CommandContext<CommandSourceStack> ctx, String name) throws CommandSyntaxException {
        PlayerFaction pf = FactionHelper.get(ctx.getSource().getPlayerOrException());
        pf.acceptInvite(name, ctx.getSource().getPlayerOrException());
    }


    @SubscribeEvent
    public static void commandRegister(final RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

}
