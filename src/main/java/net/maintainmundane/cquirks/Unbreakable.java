package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import java.util.Random;

public class Unbreakable extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Unbreakable() {
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

    public static float getDamageReduction(int level) {
        return switch (level) {
            case 1 -> 0.1F;
            case 2 -> 0.17F;
            case 3 -> 0.23F;
            default -> 0.0F;
        };
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        float damageReduction = getDamageReduction(level);

        int damageToReduce = (int) (stack.getMaxDamage() * damageReduction);
        stack.setDamage(stack.getDamage() - damageToReduce);

        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Unbreakable " + level);
    }

    @Override
    public String getTooltip() {
        return "Unbreaking";
    }
}
