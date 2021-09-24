package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class InvisibilityCloakItem extends Item implements Wearable {

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
            return InvisibilityCloakItem.dispenseCloak(blockSource, stack) ? stack : super.execute(blockSource, stack);
        }
    };

    public InvisibilityCloakItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    public static boolean dispenseCloak(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            ItemStack itemstack = stack.split(1);
            livingentity.setItemSlot(ModConstants.CLOAK_EQUIPMENT_SLOT, itemstack);
            if (livingentity instanceof Mob) {
                ((Mob)livingentity).setDropChance(ModConstants.CLOAK_EQUIPMENT_SLOT, 2.0F);
                ((Mob)livingentity).setPersistenceRequired();
            }
            return true;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean selected) {
        if(entity instanceof Player && ModUtils.hasCloak((Player) entity)) {
            ((Player) entity).addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0, false, false, false));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.getItemBySlot(ModConstants.CLOAK_EQUIPMENT_SLOT).isEmpty()) {
            player.setItemSlot(ModConstants.CLOAK_EQUIPMENT_SLOT, itemstack.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            itemstack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY).withStyle(ChatFormatting.GRAY));
    }

    @Nullable
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return ModConstants.CLOAK_EQUIPMENT_SLOT;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) { return false; }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity livingEntity) {
        return livingEntity instanceof Player ? ModUtils.hasCloak((Player) livingEntity) : false;
    }
}
