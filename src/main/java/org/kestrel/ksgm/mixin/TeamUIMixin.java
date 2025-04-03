package org.kestrel.ksgm.mixin;


/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.kestrel.ksgm.client.UI.Team.TeamDotRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({
    EntityRenderer.class
})
@Debug(export = true)
public abstract class TeamUIMixin <T extends Entity> {
    @Final
    @Shadow
    protected EntityRenderDispatcher entityRenderDispatcher;
    public TeamUIMixin() {


    }

        @Inject(
                method = {"render"},
                at = {@At(value = "INVOKE", target = "Lnet/neoforged/bus/api/IEventBus;post(Lnet/neoforged/bus/api/Event;)Lnet/neoforged/bus/api/Event;", shift = At.Shift.AFTER)}
        )

    public void DotTest(T p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
            TeamDotRenderer.renderDot(p_entity, poseStack, bufferSource, partialTick, this.entityRenderDispatcher);
        }

}
