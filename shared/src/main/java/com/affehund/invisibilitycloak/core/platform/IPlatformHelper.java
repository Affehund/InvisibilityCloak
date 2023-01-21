package com.affehund.invisibilitycloak.core.platform;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IPlatformHelper {
    boolean isModLoaded(String modId);

    boolean isInvisibleForMobs();

    boolean hideFlameAnimation();

    boolean hideHitbox();

    boolean hideShadow();

    boolean showTooltip();

    ItemStack getCloakInAdditionalSlot(Player player);
}
