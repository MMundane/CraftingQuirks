package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class Replenishing extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Replenishing() {
        super(Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    public static int getRecoveryTime(int level) {
        return switch (level) {
            case 1 -> 69 * 20;
            case 2 -> 56 * 20;
            case 3 -> 35 * 20;
            default -> 0;
        };
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putInt("ReplenishTicks", getRecoveryTime(level));

        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Replenishing " + level);
    }

    @Override
    public String getTooltip() {
        return "Replenishing";
    }
}
