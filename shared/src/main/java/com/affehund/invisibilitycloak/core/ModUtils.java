package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.ModConstants;
import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import com.affehund.invisibilitycloak.core.platform.Services;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ModUtils {
    public static boolean isValidCloak(ItemStack stack) {
        return stack != null && stack.getItem() instanceof InvisibilityCloakItem && InvisibilityCloakItem.isUsable(stack);
    }

    public static boolean hasCloakInChestSlot(Player player) {
        return isValidCloak(getChestSlotItem(player));
    }

    public static boolean hasCloakInAdditionalSlot(Player player) {
        return isValidCloak(getCloakInAdditionalSlot(player));
    }

    public static boolean hasCloakEquipped(Player player) {
        return isValidCloak(getEquippedCloakItem(player));
    }

    public static ItemStack getCloakInAdditionalSlot(Player player) {
        return Services.PLATFORM.getCloakInAdditionalSlot(player);
    }

    public static ItemStack getChestSlotItem(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST);
    }

    public static ItemStack getEquippedCloakItem(Player player) {
        if (hasCloakInAdditionalSlot(player)) {
            return getCloakInAdditionalSlot(player);
        } else if (hasCloakInChestSlot(player)) {
            return getChestSlotItem(player);
        }
        return null;
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
