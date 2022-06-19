package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.common.item.InvisibilityCloakItem;
import com.affehund.invisibilitycloak.core.InvisibilityCloakConfig;
import com.affehund.invisibilitycloak.core.InvisibilityCloakDataGeneration;
import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod(ModConstants.MOD_ID)
public class InvisibilityCloakForge {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModConstants.MOD_ID);

    public static final RegistryObject<Item> INVISIBILITY_CLOAK_ITEM = ITEMS.register(ModConstants.CLOAK_OF_INVISIBILITY,
            () -> new InvisibilityCloakItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.UNCOMMON).setNoRepair()) {
                @Override
                public EquipmentSlot getEquipmentSlot(ItemStack stack) {
                    return EquipmentSlot.CHEST;
                }
            });

    public InvisibilityCloakForge() {
        ModConstants.LOGGER.debug("Loading up {}...", ModConstants.MOD_NAME);

        var forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addGenericListener(ItemStack.class, this::attachCaps);
        forgeEventBus.register(this);

        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::gatherData);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, InvisibilityCloakConfig.COMMON_SPEC);

        ModConstants.LOGGER.debug("{} has finished loading for now.", ModConstants.MOD_NAME);
    }

    private void attachCaps(AttachCapabilitiesEvent<ItemStack> event) {
        if (ModUtils.isCuriosLoaded()) {
            var stack = event.getObject();
            var item = stack.getItem();
            if (item.equals(INVISIBILITY_CLOAK_ITEM.get())) {
                event.addCapability(new ResourceLocation(ModConstants.MOD_ID, ModConstants.CURIOS_MOD_ID), new ICapabilityProvider() {
                    final ICurio curio = new ICurio() {
                        @Override
                        public boolean canEquipFromUse(SlotContext slotContext) {
                            return true;
                        }

                        @Override
                        public ItemStack getStack() {
                            return stack;
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

    private void enqueueIMC(InterModEnqueueEvent event) {
        if (ModUtils.isCuriosLoaded()) {
            InterModComms.sendTo(ModConstants.CURIOS_MOD_ID, SlotTypeMessage.REGISTER_TYPE,
                    () -> SlotTypePreset.BACK.getMessageBuilder().build());
            ModConstants.LOGGER.debug("Enqueued IMC from {} to {}", ModConstants.MOD_ID, ModConstants.CURIOS_MOD_ID);
        }
    }

    private void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var isClientProvider = event.includeClient();

        generator.addProvider(isClientProvider, new InvisibilityCloakDataGeneration.RecipeGen(generator));
        generator.addProvider(isClientProvider, new InvisibilityCloakDataGeneration.LanguageGen(generator, "de_de"));
        generator.addProvider(isClientProvider, new InvisibilityCloakDataGeneration.LanguageGen(generator, "en_us"));
        generator.addProvider(isClientProvider, new InvisibilityCloakDataGeneration.ItemModelGen(generator, ModConstants.MOD_ID, existingFileHelper));
    }
}