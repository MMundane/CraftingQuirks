package net.maintainmundane.cquirks;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

import java.util.ArrayList;
import java.util.Random;

public class CraftEffectPicker {
    private static final ArrayList<CraftEffect> effects = new ArrayList<>();
    private static final Random random = new Random();

    public static void registerEffect(CraftEffect effect) {
        effects.add(effect);
    }

    public static boolean isToolOrArmor(Item item) {
        return item instanceof ToolItem || item instanceof ArmorItem;
    }

    public static void applyRandomEffect(ItemStack stack) {
        if (effects.isEmpty()) return;

        if (stack.getOrCreateNbt().getBoolean("CraftedEffectApplied")) {
            return;
        }

        // 67% chance to apply an enchantment
        if (random.nextFloat() > 0.67) {
            return;
        }

        ArrayList<CraftEffect> applicableEffects = new ArrayList<>();
        for (CraftEffect effect : effects) {
            if (effect.canApply(stack)) {
                applicableEffects.add(effect);
            }
        }

        if (applicableEffects.isEmpty()) return;

        int index = random.nextInt(applicableEffects.size());
        CraftEffect effect = applicableEffects.get(index);
        effect.applyEffect(stack);

        stack.getOrCreateNbt().putBoolean("CraftedEffectApplied", true);
    }
}