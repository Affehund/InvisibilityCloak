package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod(ModConstants.MOD_ID)
public class InvisibilityCloak {
    private static final Logger LOGGER = LogManager.getLogger(ModConstants.MOD_NAME);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            ModConstants.MOD_ID);

    public static final RegistryObject<Item> INVISIBILITY_CLOAK_ITEM = ITEMS.register(ModConstants.CLOAK_OF_INVISIBILITY,
            () -> new InvisibilityCloakItem(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_COMBAT).rarity(Rarity.UNCOMMON).setNoRepair()));

    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    IEventBus forgeEventBus =MinecraftForge.EVENT_BUS;

    public InvisibilityCloak() {
        LOGGER.debug("Loading up {}", ModConstants.MOD_ID);

        ITEMS.register(modEventBus);
        modEventBus.addListener(this::enqueueIMC);

        forgeEventBus.addGenericListener(ItemStack.class, this::attachCaps);
        forgeEventBus.register(this);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        if (ModUtils.isCuriosLoaded()) {
            InterModComms.sendTo(ModConstants.CURIOS_MOD_ID, SlotTypeMessage.REGISTER_TYPE,
                    () -> SlotTypePreset.BACK.getMessageBuilder().build());
            LOGGER.debug("Enqueued IMC to {}", ModConstants.CURIOS_MOD_ID);
        }
    }

    private void attachCaps(AttachCapabilitiesEvent<ItemStack> event) {
        if (ModUtils.isCuriosLoaded()) {
            ItemStack stack = event.getObject();
            Item item = stack.getItem();
            if (item.getRegistryName() != null && item.equals(INVISIBILITY_CLOAK_ITEM.get())) {
                LOGGER.debug("Attached Curios Capability to {}", item.getRegistryName());
                event.addCapability(new ResourceLocation(ModConstants.MOD_ID, item.getRegistryName().getPath() + "_curios"), new ICapabilityProvider() {
                    final ICurio curio = new ICurio() {
                        @Override
                        public boolean canEquipFromUse(SlotContext slotContext) {
                            return true;
                        }
                    };

                    @Nonnull
                    @Override
                    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                        return CuriosCapability.ITEM.orEmpty(cap, LazyOptional.of(() -> curio));
                    }
                });
            }
        }
    }
}
