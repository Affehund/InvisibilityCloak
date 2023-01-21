package com.affehund.invisibilitycloak.core.platform;

import com.affehund.invisibilitycloak.InvisibilityCloakForge;
import com.affehund.invisibilitycloak.core.InvisibilityCloakConfig;
import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get() != null && ModList.get().getModContainerById(modId).isPresent();
    }

    @Override
    public boolean isInvisibleForMobs() {
        return InvisibilityCloakConfig.INVISIBLE_FOR_MOBS.get();
    }

    @Override
    public boolean hideFlameAnimation() {
        return InvisibilityCloakConfig.HIDE_FLAME_ANIMATION.get();
    }

    @Override
    public boolean hideHitbox() {
        return InvisibilityCloakConfig.HIDE_HITBOX.get();
    }

    @Override
    public boolean hideShadow() {
        return InvisibilityCloakConfig.HIDE_SHADOW.get();
    }

    @Override
    public boolean showTooltip() {
        return InvisibilityCloakConfig.SHOW_TOOLTIP.get();
    }

    @Override
    public ItemStack getCloakInAdditionalSlot(Player player) {
        return ModUtils.isCuriosLoaded() ? CuriosApi.getCuriosHelper().findFirstCurio(player, InvisibilityCloakForge.INVISIBILITY_CLOAK_ITEM.get()).map(SlotResult::stack).orElse(null) : null;
    }
}
