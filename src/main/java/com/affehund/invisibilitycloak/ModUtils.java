package com.affehund.invisibilitycloak;

import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class ModUtils {
    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    public static boolean isCuriosLoaded() {
        return isModLoaded(ModConstants.CURIOS_MOD_ID);
    }

    public static boolean hasCuriosItem(PlayerEntity player, Item item) {
       if(isCuriosLoaded()) {
           return CuriosApi.getCuriosHelper().findEquippedCurio(item, player).isPresent();
        }
        return false;
    }

    public static boolean isTrinketsLoaded() {
        return isModLoaded(ModConstants.TRINKETS_MOD_ID);
    }

    public static boolean hasTrinketsItem(PlayerEntity player, Item item) {
        if(isTrinketsLoaded()) {
            return TrinketsApi.getTrinketComponent(player).getStack(SlotGroups.CHEST, Slots.CAPE).getItem().equals(item);
        }
        return false;
    }

    public static boolean hasCloak(PlayerEntity player) {
        Item item = InvisibilityCloak.INVISIBILITY_CLOAK_ITEM;
        return player.getEquippedStack(ModConstants.CLOAK_EQUIPMENT_SLOT).getItem().equals(item) || hasCuriosItem(player, item) || hasTrinketsItem(player, item);
    }
}
