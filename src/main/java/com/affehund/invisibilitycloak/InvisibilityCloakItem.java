package com.affehund.invisibilitycloak;

import com.affehund.invisibilitycloak.core.ModUtils;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class InvisibilityCloakItem extends Item implements IArmorVanishable {

    public static final IDispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(IBlockSource blockSource, ItemStack stack) {
            return InvisibilityCloakItem.dispenseCloak(blockSource, stack) ? stack : super.execute(blockSource, stack);
        }
    };

    public InvisibilityCloakItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    public static boolean dispenseCloak(IBlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(blockpos), EntityPredicates.NO_SPECTATORS.and(new EntityPredicates.ArmoredMob(stack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            ItemStack itemstack = stack.split(1);
            livingentity.setItemSlot(ModConstants.CLOAK_EQUIPMENT_SLOT, itemstack);
            if (livingentity instanceof MobEntity ) {
                ((MobEntity)livingentity).setDropChance(ModConstants.CLOAK_EQUIPMENT_SLOT, 2.0F);
                ((MobEntity)livingentity).setPersistenceRequired();
            }
            return true;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean selected) {
        if(entity instanceof PlayerEntity && ModUtils.hasCloak((PlayerEntity) entity)) {
            ((PlayerEntity) entity).addEffect(new EffectInstance(Effects.INVISIBILITY, 20, 0, false, false, false));
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.getItemBySlot(ModConstants.CLOAK_EQUIPMENT_SLOT).isEmpty()) {
            player.setItemSlot(ModConstants.CLOAK_EQUIPMENT_SLOT, itemstack.copy());
            if (!world.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            itemstack.setCount(0);
            return ActionResult.sidedSuccess(itemstack, world.isClientSide());
        } else {
            return ActionResult.fail(itemstack);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent(ModConstants.TOOLTIP_CLOAK_OF_INVISIBILITY).withStyle(TextFormatting.GRAY));
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return ModConstants.CLOAK_EQUIPMENT_SLOT;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) { return false; }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity livingEntity) {
        return livingEntity instanceof PlayerEntity ? ModUtils.hasCloak((PlayerEntity) livingEntity) : false;
    }
}
