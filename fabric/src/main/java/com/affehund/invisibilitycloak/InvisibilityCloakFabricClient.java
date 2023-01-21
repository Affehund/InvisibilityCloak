package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class InvisibilityCloakFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemProperties.register(InvisibilityCloakFabric.INVISIBILITY_CLOAK_ITEM, new ResourceLocation(ModConstants.MOD_ID, "broken"), (itemStack, clientLevel, livingEntity, i) -> InvisibilityCloakItem.isUsable(itemStack) ? 0 : 1);
    }
}
