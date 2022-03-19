package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.ModConstants;
import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import com.affehund.invisibilitycloak.core.platform.Services;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class ModUtils {

    public static boolean hasCloak(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof InvisibilityCloakItem || Services.PLATFORM.hasCloakCharm(player);
    }

    public static boolean isModLoaded(String modId) {
        return Services.PLATFORM.isModLoaded(modId);
    }

    public static boolean isTrinketsLoaded() {
        return isModLoaded(ModConstants.TRINKETS_MOD_ID);
    }

    public static boolean isCuriosLoaded() {
        return isModLoaded(ModConstants.CURIOS_MOD_ID);
    }

    public static boolean isInvisibleForMobs() {
        return Services.PLATFORM.isInvisibleForMobs();
    }

    public static boolean hideFlameAnimation() {
        return Services.PLATFORM.hideFlameAnimation();
    }

    public static boolean hideHitbox() {
        return Services.PLATFORM.hideHitbox();
    }

    public static boolean hideShadow() {
        return Services.PLATFORM.hideShadow();
    }

    public static boolean showTooltip() {
        return Services.PLATFORM.showTooltip();
    }
}
