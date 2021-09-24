package com.affehund.invisibilitycloak;

import dev.emi.trinkets.api.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;
import top.theillusivec4.curios.api.SlotTypePreset;

public class InvisibilityCloak implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(ModConstants.MOD_NAME);

    public static final InvisibilityCloakItem INVISIBILITY_CLOAK_ITEM = createInvisibilityCloak();

    private static InvisibilityCloakItem createInvisibilityCloak() {
        FabricItemSettings settings = new FabricItemSettings()
                .maxCount(1)
                .rarity(Rarity.UNCOMMON)
                .group(ItemGroup.COMBAT).equipmentSlot((slot ->
                        EquipmentSlot.CHEST));
/*        if (ModUtils.isTrinketsLoaded())
            return new InvisibilityCloakTrinketItem(settings);
        else*/
            return new InvisibilityCloakItem(settings);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, ModConstants.CLOAK_OF_INVISIBILITY),
                INVISIBILITY_CLOAK_ITEM);

        if (ModUtils.isTrinketsLoaded()) {
            TrinketSlots.addSlot(SlotGroups.CHEST, Slots.CAPE, new Identifier("trinkets", "textures/item/empty_trinket_slot_cape.png"), (slot, stack) -> {
                if (!(stack.getItem() instanceof Trinket)) {
                    return stack.getItem().equals(INVISIBILITY_CLOAK_ITEM);
                }
                return ((Trinket) stack.getItem()).canWearInSlot(slot.getSlotGroup().getName(), slot.getName());
            });
        }

        if (ModUtils.isCuriosLoaded()) {
            CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, SlotTypePreset.BACK.getInfoBuilder().build());
            LOGGER.debug("Enqueued IMC to {}", ModConstants.CURIOS_MOD_ID);
        }
    }
}
