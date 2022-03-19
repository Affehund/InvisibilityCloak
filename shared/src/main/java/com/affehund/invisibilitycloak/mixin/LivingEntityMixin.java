package com.affehund.invisibilitycloak.mixin;

import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "canBeSeenAsEnemy()Z", at = @At("HEAD"), cancellable = true)
    private void canBeSeenAsEnemy(CallbackInfoReturnable<Boolean> cir) {
        if (ModUtils.isInvisibleForMobs() && (LivingEntity) (Object) this instanceof Player player && ModUtils.hasCloak(player))
            cir.setReturnValue(false);
    }
}
