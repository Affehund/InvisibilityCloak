package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.ModConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEventHandler {
    @SubscribeEvent
    public static void preRenderPlayer(final RenderPlayerEvent.Pre event) {
        if(ModUtils.hasCloak(event.getPlayer())) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderHand(final RenderHandEvent event) {
        if(ModUtils.hasCloak(Minecraft.getInstance().player))
            event.setCanceled(true);
    }
}
