package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.InvisibilityCloakForge;
import com.affehund.invisibilitycloak.ModConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public class InvisibilityCloakDataGeneration {
    public static class LanguageGen extends LanguageProvider {

        public LanguageGen(DataGenerator gen, String locale) {
            super(gen, ModConstants.MOD_ID, locale);
        }

        @Override
        protected void addTranslations() {
            String locale = this.getName().replace("Languages: ", "");
            switch (locale) {
                case "de_de" -> {
                    add("_comment", "Translation (de_de) by Affehund");
                    add(InvisibilityCloakForge.INVISIBILITY_CLOAK_ITEM.get(), "Tarnumhang");
                    add(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY, "Für völlige Unsichtbarkeit Umhang auf der Brust tragen.");
                }
                case "en_us" -> {
                    add("_comment", "Translation (en_us) by Affehund");
                    add(InvisibilityCloakForge.INVISIBILITY_CLOAK_ITEM.get(), "Cloak of Invisibility");
                    add(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY, "Wear this on your chest to become completely invisible.");
                    addAutoConfigOption("INVISIBLE_FOR_MOBS");
                    addAutoConfigOption("HIDE_FLAME_ANIMATION");
                    addAutoConfigOption("HIDE_HITBOX");
                    addAutoConfigOption("HIDE_SHADOW");
                    addAutoConfigOption("SHOW_TOOLTIP");
                }
            }
        }

        private void addAutoConfigOption(String key) {
            add("text.autoconfig." + ModConstants.MOD_ID + ".option." + key, StringUtils.capitalizeFirstLetter(key.toLowerCase(Locale.ROOT).replace("_", " ")));
        }
    }

    public static class RecipeGen extends RecipeProvider {
        public RecipeGen(DataGenerator gen) {
            super(gen);
        }

        @Override
        protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
            ShapedRecipeBuilder.shaped(InvisibilityCloakForge.INVISIBILITY_CLOAK_ITEM.get()).pattern("fsf").pattern("ded").pattern("f f")
                    .define('f', Items.FEATHER).define('s', Items.STRING).define('d', Items.BLACK_DYE)
                    .define('e', Items.ELYTRA).unlockedBy("has_elytra", has(Items.ELYTRA)).save(consumer);
        }
    }

    public static class ItemModelGen extends ItemModelProvider {

        public ItemModelGen(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
            super(generator, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            this.singleTexture(InvisibilityCloakForge.INVISIBILITY_CLOAK_ITEM.get());
        }

        private void singleTexture(Item item) {
            var registryName = item.getRegistryName();
            this.singleTexture(Objects.requireNonNull(registryName).getPath(), new ResourceLocation("item/generated"), "layer0", this.modLoc("item/" + registryName.getPath()));
        }
    }
}
