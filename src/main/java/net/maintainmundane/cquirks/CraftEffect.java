package net.maintainmundane.cquirks;

import net.minecraft.item.*;

public interface CraftEffect {
    void applyEffect(ItemStack stack);

    String getTooltip();

    default boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem || stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ToolItem;    }
}

