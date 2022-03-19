package com.affehund.invisibilitycloak.core.platform;

import com.affehund.invisibilitycloak.InvisibilityCloakFabric;
import com.affehund.invisibilitycloak.core.ModUtils;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean hasCloakCharm(Player player) {
        return ModUtils.isTrinketsLoaded() && TrinketsApi.getTrinketComponent(player).map(c -> c.isEquipped(InvisibilityCloakFabric.INVISIBILITY_CLOAK_ITEM)).orElse(false);
    }

    @Override
    public boolean isInvisibleForMobs() {
        return InvisibilityCloakFabric.CONFIG.INVISIBLE_FOR_MOBS;
    }

    @Override
    public boolean hideFlameAnimation() {
        return InvisibilityCloakFabric.CONFIG.HIDE_FLAME_ANIMATION;
    }

    @Override
    public boolean hideHitbox() {
        return InvisibilityCloakFabric.CONFIG.HIDE_HITBOX;
    }

    @Override
    public boolean hideShadow() {
        return InvisibilityCloakFabric.CONFIG.HIDE_SHADOW;
    }

    @Override
    public boolean showTooltip() {
        return InvisibilityCloakFabric.CONFIG.SHOW_TOOLTIP;
    }
}
