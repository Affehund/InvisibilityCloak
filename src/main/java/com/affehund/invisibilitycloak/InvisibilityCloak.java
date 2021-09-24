package com.affehund.invisibilitycloak;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class InvisibilityCloak implements ModInitializer {
    public static final Item INVISIBILITY_CLOAK_ITEM = new InvisibilityCloakItem(
            new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).equipmentSlot((stack ->
                    EquipmentSlot.CHEST)));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, ModConstants.CLOAK_OF_INVISIBILITY),
                INVISIBILITY_CLOAK_ITEM);
    }
}
