package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.core.ModDataGeneration;
import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
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

    //TODO: canBeSeenByAnyone mixin in LivingEntity class or similar to prevent visibility for attacking mobs

    private static final Logger LOGGER = LogManager.getLogger(ModConstants.MOD_NAME);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            ModConstants.MOD_ID);

    public static final RegistryObject<Item> INVISIBILITY_CLOAK_ITEM = ITEMS.register(ModConstants.CLOAK_OF_INVISIBILITY,
            () -> new InvisibilityCloakItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.UNCOMMON).setNoRepair()));


    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    IEventBus forgeEventBus =MinecraftForge.EVENT_BUS;

    public InvisibilityCloak() {
        LOGGER.debug("Loading up {}", ModConstants.MOD_ID);
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::gatherData);

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

    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) { // server side generators
            generator.addProvider(new ModDataGeneration.RecipeGen(generator));
        }
        if (event.includeClient()) { // client side generators
            generator.addProvider(new ModDataGeneration.LanguageGen(generator, "de_de"));
            generator.addProvider(new ModDataGeneration.LanguageGen(generator, "en_us"));
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
                        public ItemStack getStack() {
                            return stack;
                        }

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
