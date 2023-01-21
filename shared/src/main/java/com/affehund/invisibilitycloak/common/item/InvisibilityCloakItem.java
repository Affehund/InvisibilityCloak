package com.affehund.invisibilitycloak.common.item;

import com.affehund.invisibilitycloak.ModConstants;
import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvisibilityCloakItem extends Item implements Wearable {

    public InvisibilityCloakItem(Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static boolean isUsable(ItemStack itemStack) {
        return !itemStack.isDamageableItem() || itemStack.getDamageValue() < itemStack.getMaxDamage() - 1;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
        var itemStack = player.getItemInHand(interactionHand);
        var equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);

        if (player.getItemBySlot(equipmentSlot).isEmpty()) {
            player.setItemSlot(equipmentSlot, itemStack.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            itemStack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (ModUtils.showTooltip()) {
            tooltip.add(Component.translatable(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }

    // on piece of membrane repairs by 188 durability points
    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack1, ItemStack stack2) {
        return stack2.is(Items.PHANTOM_MEMBRANE);
    }
}
