package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import com.affehund.invisibilitycloak.core.config.InvisibilityCloakConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class InvisibilityCloakFabric implements ModInitializer {

    public static final Item INVISIBILITY_CLOAK_ITEM = new InvisibilityCloakItem(new FabricItemSettings().group(CreativeModeTab.TAB_COMBAT).maxCount(1).maxDamage(ModConstants.CLOAK_DURABILITY).rarity(Rarity.UNCOMMON).equipmentSlot((stack -> EquipmentSlot.CHEST)));

    public static InvisibilityCloakConfig CONFIG;

    @Override
    public void onInitialize() {
        ModConstants.LOGGER.debug("Loading up {}...", ModConstants.MOD_NAME);

        Registry.register(Registry.ITEM, new ResourceLocation(ModConstants.MOD_ID, ModConstants.CLOAK_OF_INVISIBILITY), INVISIBILITY_CLOAK_ITEM);
        AutoConfig.register(InvisibilityCloakConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(InvisibilityCloakConfig.class).getConfig();

        ModConstants.LOGGER.debug("{} has finished loading for now.", ModConstants.MOD_NAME);
    }
}
