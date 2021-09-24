package com.affehund.invisibilitycloak;

import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class ModUtils {
    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    public static boolean isTrinketsLoaded() {
        return isModLoaded(ModConstants.TRINKETS_MOD_ID);
    }

    public static boolean hasTrinketsItem(PlayerEntity player, Item item) {
        if(isTrinketsLoaded()) {
            return TrinketsApi.getTrinketComponent(player).get().isEquipped(item);
        }
        return false;
    }

    public static boolean hasCloak(PlayerEntity player) {
        Item item = InvisibilityCloak.INVISIBILITY_CLOAK_ITEM;
        return player.getEquippedStack(ModConstants.CLOAK_EQUIPMENT_SLOT).getItem().equals(item) || hasTrinketsItem(player, item);
    }
}
