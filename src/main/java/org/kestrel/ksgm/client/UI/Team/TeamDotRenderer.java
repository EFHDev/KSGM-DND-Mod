package org.kestrel.ksgm.client.UI.Team;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.kestrel.ksgm.API.Faction.IFaction;
import org.kestrel.ksgm.Server.Factions.Attachment.PlayerFaction;
import org.kestrel.ksgm.common.attachments.ModAttachments;

public class TeamDotRenderer {

    private static boolean toggle = true;


    public static <T extends Entity> void renderDot(
            T entity, PoseStack poseStack, MultiBufferSource bufferSource, float partialTick,
            EntityRenderDispatcher entityRenderDispatcher) {
        if (entity instanceof Player player) {
            PlayerFaction pf = (PlayerFaction) Minecraft.getInstance().player.getData(ModAttachments.PLAYER_FACTION);
            PlayerFaction opf = (PlayerFaction) player.getData(ModAttachments.PLAYER_FACTION);
            Minecraft.getInstance().player.displayClientMessage(Component.literal("Me: " + pf.getId() + " Target Player ID: " + opf.getId() + " Name:" + player.getName() + " Other: " + TeamHolder.isFriend(player.getUUID())), true);

            if (!TeamHolder.isFriend(player.getUUID())) return;
            if (!toggle) return;


            double distancePlayer = entityRenderDispatcher.distanceToSqr(player);
            if (isInDotRenderDistance(player, distancePlayer)) {
                Vec3 vec3 = player.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getViewYRot(partialTick));
                if (vec3 != null) {
                    if (!player.isDiscrete() && !player.isSpectator() && !player.isInvisible() && !player.isInvulnerable()) {
                        if (distancePlayer < 120) return;
                        poseStack.pushPose();
                        poseStack.translate(vec3.x, vec3.y + 0.9, vec3.z);
                        poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
                        poseStack.scale(0.025F, -0.025F, 0.025F);
                        Matrix4f matrix4f = poseStack.last().pose();

                        int[] colorInt = TeamHolder.getFaction(player.getUUID());


//                        Minecraft.getInstance().player.displayClientMessage(Component.literal("Color: " + colorInt + "Target Player: " + opf.getName() + "Self: " + pf.getName()), false);

                        float r = colorInt[0] / 255.0f;
                        float g = colorInt[1] / 255.0f;
                        float b = colorInt[2] / 255.0f;
                        float distance = (float) Math.sqrt(distancePlayer);
                        float a = 1.0F;

                        float k = 0.5f; // ratio
                        float size = k * distance;
                        float rectX1 = -size;
                        float rectY1 = -size;
                        float rectX2 = size;
                        float rectY2 = size;


                        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.guiOverlay());
                        addColoredRect(matrix4f, vertexConsumer, (float) rectX1, (float) rectY1, rectX2 - rectX1, rectY2 - rectY1, (float) r, (float) g, (float) b, a);
                        poseStack.popPose();
                    }

                }

            }
        }
    }

    public static boolean isInDotRenderDistance(Entity entity, double squareDistance) {
        if (entity instanceof LivingEntity) {
            return !(squareDistance > 10096.0);
        } else return false;
    }

    public static void addColoredRect(Matrix4f matrix, VertexConsumer vertexBuffer, float x, float y, float w, float h, float r, float g, float b, float a) {
        vertexBuffer.addVertex(matrix, x, y + (float) h, 0.0F).setColor(r, g, b, a);
        vertexBuffer.addVertex(matrix, x + (float) w, y + (float) h, 0.0F).setColor(r, g, b, a);
        vertexBuffer.addVertex(matrix, x + (float) w, y, 0.0F).setColor(r, g, b, a);
        vertexBuffer.addVertex(matrix, x, y, 0.0F).setColor(r, g, b, a);
    }


    public static void toggleUI() {
        TeamDotRenderer.toggle = !toggle;
    }


    public static boolean isToggled() {
        return toggle;
    }
}