package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class Swift extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Swift() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }


    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    public static float getSpeedIncrease(int level) {
        return switch (level) {
            case 1 -> 1.11F;
            case 2 -> 1.22F;
            case 3 -> 1.33F;
            default -> 1.0F;
        };
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putInt("HideFlags", 1);  
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Swift " + level);
    }

    @Override
    public String getTooltip() {
        return "Swift";
    }

}
