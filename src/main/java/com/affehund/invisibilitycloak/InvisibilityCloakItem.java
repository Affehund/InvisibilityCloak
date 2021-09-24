package com.affehund.invisibilitycloak;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InvisibilityCloakItem extends Item implements Wearable {
    public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior() {
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            return InvisibilityCloakItem.dispenseCloak(pointer, stack) ? stack : super.dispenseSilently(pointer, stack);
        }
    };

    public InvisibilityCloakItem(Item.Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    public static boolean dispenseCloak(BlockPointer pointer, ItemStack armor) {
        BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
        List<LivingEntity> list = pointer.getWorld().getEntitiesByClass(LivingEntity.class, new Box(blockPos), EntityPredicates.EXCEPT_SPECTATOR.and(new EntityPredicates.Equipable(armor)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingEntity = list.get(0);
            ItemStack itemStack = armor.split(1);
            livingEntity.equipStack(ModConstants.CLOAK_EQUIPMENT_SLOT, itemStack);
            if (livingEntity instanceof MobEntity) {
                ((MobEntity)livingEntity).setEquipmentDropChance(ModConstants.CLOAK_EQUIPMENT_SLOT, 2.0F);
                ((MobEntity)livingEntity).setPersistent();
            }
            return true;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean selected) {
        if(entity instanceof PlayerEntity && ModUtils.hasCloak((PlayerEntity) entity)) {
            ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 20, 0, false, false, false));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.getEquippedStack(ModConstants.CLOAK_EQUIPMENT_SLOT).isEmpty()) {
            user.equipStack(ModConstants.CLOAK_EQUIPMENT_SLOT, itemStack.copy());
            itemStack.setCount(0);
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY).formatted(Formatting.GRAY));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
