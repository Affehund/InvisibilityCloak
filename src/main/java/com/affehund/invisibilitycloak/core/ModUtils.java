package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.InvisibilityCloak;
import com.affehund.invisibilitycloak.ModConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

public class ModUtils {
    public static boolean isModLoaded(String modID) {
        return ModList.get() != null && ModList.get().getModContainerById(modID).isPresent();
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

    public static boolean hasCloak(PlayerEntity player) {
        Item item = InvisibilityCloak.INVISIBILITY_CLOAK_ITEM.get();
        return player.getItemBySlot(ModConstants.CLOAK_EQUIPMENT_SLOT).getItem().equals(item) || hasCuriosItem(player, item);
    }
}
