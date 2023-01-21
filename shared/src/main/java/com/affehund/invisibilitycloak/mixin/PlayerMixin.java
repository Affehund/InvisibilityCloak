package com.affehund.invisibilitycloak.mixin;

import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    private int cloakTicks;
    private boolean isWearingCloak;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void invisibilityCloak$tick(CallbackInfo ci) {
        if (this.isWearingCloak) {
            ++this.cloakTicks;
        } else {
            this.cloakTicks = 0;
        }
    }

    @Inject(method = "aiStep()V", at = @At("HEAD"))
    private void invisibilityCloak$aiStep(CallbackInfo ci) {
        Player player = (Player) (Object) this;

        var itemStack = ModUtils.getEquippedCloakItem(player);
        if (itemStack != null && itemStack.getItem() instanceof InvisibilityCloakItem) {
            this.isWearingCloak = true;
            /*
                durability increases by one point each second
                12min 22s without enchantments
                49min 28s with unbreaking 3
            */
            if (!player.level.isClientSide && InvisibilityCloakItem.isUsable(itemStack) && (this.cloakTicks + 1) % 20 == 0) {
                itemStack.hurtAndBreak(1, player, (consumer) -> consumer.broadcastBreakEvent(EquipmentSlot.CHEST));
            }
        } else {
            this.isWearingCloak = false;
        }
    }

    @Inject(method = "canBeSeenAsEnemy()Z", at = @At("HEAD"), cancellable = true)
    private void invisibilityCloak$canBeSeenAsEnemy(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (ModUtils.isInvisibleForMobs() && ModUtils.hasCloakEquipped(player)) {
            cir.setReturnValue(false);
        }
    }
}
