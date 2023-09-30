package net.maintainmundane.cquirks;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import java.util.Random;

public class Freezing extends Enchantment implements CraftEffect {

    
    static {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient && entity instanceof LivingEntity) {
                ItemStack stack = player.getMainHandStack();
                if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem) {
                    int level = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.FREEZING), stack);
                    if (level > 0) {
                        applyFreezingEffect((LivingEntity) entity, level);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
    private static final Random RANDOM = new Random();

    public Freezing() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem;
    }

    public static void applyFreezingEffect(LivingEntity target, int level) {
        if (level > 0) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 72, level -1, false, false, false));
        }
    }
    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;
        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Freezing " + level);
    }

    @Override
    public String getTooltip() {
        return "Freezing";
    }
}
