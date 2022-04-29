package com.affehund.invisibilitycloak.core.config;

import com.affehund.invisibilitycloak.ModConstants;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = ModConstants.MOD_ID)
public class InvisibilityCloakConfig implements ConfigData {

    @Comment("Whether you are invisible for mobs when wearing the cloak of invisibility.")
    public Boolean INVISIBLE_FOR_MOBS = true;

    @Comment("Whether the flame animation is hidden when wearing the cloak.")
    public Boolean HIDE_FLAME_ANIMATION = true;

    @Comment("Whether your hitbox is hidden when wearing the cloak.")
    public Boolean HIDE_HITBOX = true;

    @Comment("Whether your hitbox is hidden when wearing the cloak.")
    public Boolean HIDE_SHADOW = true;

    @Comment("Whether to show a tooltip on the cloak.")
    public Boolean SHOW_TOOLTIP = true;
}
