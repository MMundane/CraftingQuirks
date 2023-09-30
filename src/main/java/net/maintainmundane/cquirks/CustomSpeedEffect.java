package net.maintainmundane.cquirks;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class CustomSpeedEffect extends StatusEffect {

    public CustomSpeedEffect() {
        super(
                StatusEffectCategory.BENEFICIAL,
                0x90ee90);
    }

    public static double getFast(int level) {
        return 0.0048 * (level + 1);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public static final UUID SPEED_MODIFIER_UUID = UUID.fromString("9c60a37f-68ee-4de6-b2a1-4b59a8d678a2");

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        double speedIncrease = 0;

        
        EquipmentSlot[] armorSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

        for (EquipmentSlot slot : armorSlots) {
            ItemStack itemStack = entity.getEquippedStack(slot);
            if (itemStack.hasEnchantments() && EnchantmentHelper.getLevel(YourMod.SWIFT_ENCHANTMENT, itemStack) > 0) {
                speedIncrease += getFast(EnchantmentHelper.getLevel(YourMod.SWIFT_ENCHANTMENT, itemStack));
            }
        }

        EntityAttributeModifier speedModifier = new EntityAttributeModifier(
                SPEED_MODIFIER_UUID,
                "CustomSpeedEffectModifier",
                speedIncrease,
                EntityAttributeModifier.Operation.ADDITION
        );

        
        if (Objects.requireNonNull(entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getModifier(SPEED_MODIFIER_UUID) != null) {
            Objects.requireNonNull(entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).removeModifier(SPEED_MODIFIER_UUID);
        }

        
        if (speedIncrease > 0) {
            Objects.requireNonNull(entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).addTemporaryModifier(speedModifier);
        }
    }
}