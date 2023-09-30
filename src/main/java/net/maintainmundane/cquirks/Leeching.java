package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.Random;

public class Leeching extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Leeching() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }

    public static void applyLeechingEffect(LivingEntity attacker, LivingEntity target, float damageDealt, int level) {
        if (level > 0) {
            float healthToRestore = (damageDealt / 18) * level;
            attacker.heal(healthToRestore);
        }
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Leeching " + level);
    }

    @Override
    public String getTooltip() {
        return "Leeching";
    }
}
