package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.InvisibilityCloak;
import com.affehund.invisibilitycloak.ModConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ModDataGeneration {
    public static final class LanguageGen extends LanguageProvider {

        public LanguageGen(DataGenerator gen, String locale) {
            super(gen, ModConstants.MOD_ID, locale);
        }

        @Override
        protected void addTranslations() {
            String locale = this.getName().replace("Languages: ", "");
            switch (locale) {
                case "de_de" -> {
                    add("_comment", "Translation (de_de) by Affehund");
                    add(InvisibilityCloak.INVISIBILITY_CLOAK_ITEM.get(), "Tarnumhang");
                    add(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY, "Trage den Tarnumhang auf der Brust, um völlig unsichtbar zu werden.");
                }
                case "en_us" -> {
                    add("_comment", "Translation (en_us) by Affehund");
                    add(InvisibilityCloak.INVISIBILITY_CLOAK_ITEM.get(), "Cloak of Invisibility");
                    add(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY, "Wear this on your chest to become completely invisible.");
                }
            }
        }
    }

    public static final class RecipeGen extends RecipeProvider {
        public RecipeGen(DataGenerator gen) {
            super(gen);
        }

        @Override
        protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
            ShapedRecipeBuilder.shaped(InvisibilityCloak.INVISIBILITY_CLOAK_ITEM.get()).pattern("fsf").pattern("ded").pattern("f f")
                    .define('f', Items.FEATHER).unlockedBy("has_feather", has(Items.FEATHER)).define('s', Items.STRING).unlockedBy("has_string", has(Items.STRING)).define('d', Items.BLACK_DYE)
                    .unlockedBy("has_black_dye", has(Items.BLACK_DYE)).define('e', Items.ELYTRA)
                    .unlockedBy("has_elytra", has(Items.ELYTRA)).save(consumer);
        }

    }
}
