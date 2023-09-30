package net.maintainmundane.cquirks;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;

import java.util.Random;

public class Searing extends Enchantment implements CraftEffect {

    static {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient && entity instanceof LivingEntity) {
                ItemStack stack = player.getMainHandStack();
                if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem) {

                    int level = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.SEARING), stack);
                    if (level > 0) {
                        applySearingEffect((LivingEntity) entity, level);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    private static final Random RANDOM = new Random();

    public Searing() {
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

    public static void applySearingEffect(LivingEntity target, int level) {
        if (level > 0) {
            target.setOnFireFor((int) (level * 1.3));
        }
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Searing " + level);
    }

    @Override
    public String getTooltip() {
        return "Searing";
    }
}
