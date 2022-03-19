package com.affehund.invisibilitycloak.mixin.client;

import com.affehund.invisibilitycloak.core.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "renderFlame(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void renderFlame(PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity, CallbackInfo ci) {
        if (ModUtils.hideFlameAnimation() && entity instanceof Player player && ModUtils.hasCloak(player))
            ci.cancel();
    }

    @Inject(method = "renderHitbox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;F)V", at = @At("HEAD"), cancellable = true)
    private static void renderHitbox(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float tickDelta, CallbackInfo ci) {
        if (ModUtils.hideHitbox() && entity instanceof Player player && ModUtils.hasCloak(player)) ci.cancel();
    }

    @Inject(method = "renderShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/Entity;FFLnet/minecraft/world/level/LevelReader;F)V", at = @At("HEAD"), cancellable = true)
    private static void renderShadow(PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity, float opacity, float tickDelta, LevelReader levelReader, float $$6, CallbackInfo ci) {
        if (ModUtils.hideShadow() && entity instanceof Player player && ModUtils.hasCloak(player)) ci.cancel();
    }
}
