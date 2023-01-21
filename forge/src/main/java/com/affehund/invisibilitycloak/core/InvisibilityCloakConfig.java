package com.affehund.invisibilitycloak.core;

import com.affehund.invisibilitycloak.ModConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class InvisibilityCloakConfig {

    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupCommonConfig(configBuilder);
        COMMON_SPEC = configBuilder.build();
    }

    public static ForgeConfigSpec.BooleanValue INVISIBLE_FOR_MOBS;
    public static ForgeConfigSpec.BooleanValue HIDE_FLAME_ANIMATION;
    public static ForgeConfigSpec.BooleanValue HIDE_HITBOX;
    public static ForgeConfigSpec.BooleanValue HIDE_SHADOW;
    public static ForgeConfigSpec.BooleanValue SHOW_TOOLTIP;

    private static void setupCommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment(ModConstants.MOD_NAME + "Common Config");
        INVISIBLE_FOR_MOBS = builder.comment("Whether you are invisible for mobs when wearing the cloak of invisibility.").define("invisible_for_mobs", true);
        HIDE_FLAME_ANIMATION = builder.comment("Whether the flame animation is hidden when wearing the cloak.").define("hide_flame_animation", true);
        HIDE_HITBOX = builder.comment("Whether your hitbox is hidden when wearing the cloak.").define("hide_hitbox", true);
        HIDE_SHADOW = builder.comment("Whether your shadow is hidden when wearing the cloak.").define("hide_shadow", true);
        SHOW_TOOLTIP = builder.comment("Whether to show a tooltip on the cloak.").define("show_tooltip", true);
    }
}
