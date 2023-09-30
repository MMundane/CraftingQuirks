package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.Random;

public class Sharpen extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Sharpen() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3; // You can change the maximum level as needed
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 11;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 20;
    }

    @Override
        public void applyEffect(ItemStack stack) {
            int level = RANDOM.nextInt(3) + 1;
            stack.addEnchantment(this, level);
            stack.getOrCreateNbt().putString("CraftEffectTooltip", "Sharpen " + level);
        }

        @Override
        public String getTooltip() {
            return "Swift";
        }
    }
