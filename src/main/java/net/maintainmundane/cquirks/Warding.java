package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class Warding extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Warding() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem;
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Warding " + level);
    }

    @Override
    public String getTooltip() {
        return "Warding";
    }
}
