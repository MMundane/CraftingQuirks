package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.YourMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class SharpMixin {
    @ModifyVariable(method = "applyDamage", at = @At(value = "HEAD"), ordinal = 0)
    private float modifyDamage(float originalAmount, DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity attacker) {
            ItemStack mainHandStack = attacker.getMainHandStack();
            int sharpLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.SHARPEN),  mainHandStack);

            if (sharpLevel > 0) {
                float extraDamage = (0.23F * sharpLevel) * amount; // You can adjust the percentage as needed
                return amount + extraDamage;
            }
        }
        return originalAmount;
    }
}
