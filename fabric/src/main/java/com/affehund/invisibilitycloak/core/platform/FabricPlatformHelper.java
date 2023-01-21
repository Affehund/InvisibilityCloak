package com.affehund.invisibilitycloak.core.platform;

import com.affehund.invisibilitycloak.InvisibilityCloakFabric;
import com.affehund.invisibilitycloak.core.ModUtils;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
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

    @Override
    public ItemStack getCloakInAdditionalSlot(Player player) {
        if (!ModUtils.isTrinketsLoaded()) return null;
        Optional<List<Tuple<SlotReference, ItemStack>>> optionalTuples = TrinketsApi.getTrinketComponent(player).map(t -> t.getEquipped(InvisibilityCloakFabric.INVISIBILITY_CLOAK_ITEM));
        return optionalTuples.flatMap(tuples -> tuples.stream().map(Tuple::getB).findFirst()).orElse(null);
    }
}
