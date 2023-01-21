package com.affehund.invisibilitycloak.client;

import com.affehund.invisibilitycloak.InvisibilityCloakForge;
import com.affehund.invisibilitycloak.ModConstants;
import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModConstants.MOD_ID)
public class ClientEventHandler {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(InvisibilityCloakForge.INVISIBILITY_CLOAK_ITEM.get(), new ResourceLocation(ModConstants.MOD_ID, "broken"), (itemStack, clientLevel, livingEntity, i) -> InvisibilityCloakItem.isUsable(itemStack) ? 0 : 1);
    }
}
