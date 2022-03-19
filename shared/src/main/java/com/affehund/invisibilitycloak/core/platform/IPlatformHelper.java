package com.affehund.invisibilitycloak.core.platform;

import net.minecraft.world.entity.player.Player;

public interface IPlatformHelper {
    boolean isModLoaded(String modId);

    boolean hasCloakCharm(Player player);

    boolean isInvisibleForMobs();

    boolean hideFlameAnimation();

    boolean hideHitbox();

    boolean hideShadow();

    boolean showTooltip();
}
