package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.Leeching;
import net.maintainmundane.cquirks.YourMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    public void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        
        if (source.getAttacker() instanceof LivingEntity attacker) {
            ItemStack mainHandStack = attacker.getMainHandStack();
            int leechingLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.LEECHING), mainHandStack);
            if (leechingLevel > 0) {
                Leeching.applyLeechingEffect(attacker, livingEntity, amount, leechingLevel);
            }
        }
    }

    @Inject(method = "getArmor", at = @At("RETURN"), cancellable = true)
    private void addWardingBonus(CallbackInfoReturnable<Integer> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        Iterable<ItemStack> armorItems = self.getArmorItems();

        int originalArmor = cir.getReturnValue();
        int bonusArmor = 0;

        for (ItemStack stack : armorItems) {
            int wardingLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.WARDING), stack);
            if (wardingLevel > 0) {
                bonusArmor += Math.ceil(0.67 * wardingLevel);
            }
        }
        cir.setReturnValue(originalArmor + bonusArmor);
    }


    
    @Inject(method = "computeFallDamage(FF)I", at = @At("HEAD"), cancellable = true)
    public void computeFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity instanceof PlayerEntity player) {
            int totalWindwalkerLevel = 0;
            for (ItemStack armorPiece : player.getInventory().armor) {
                totalWindwalkerLevel += EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.WINDWALKER), armorPiece);
            }

            if (totalWindwalkerLevel > 0) {
                float reductionFactor = 1 - (totalWindwalkerLevel * 0.033f);
                int originalFallDamage = Math.max(0, Math.round(fallDistance - 3.0F - damageMultiplier));
                int modifiedFallDamage = Math.round(originalFallDamage * reductionFactor);
                modifiedFallDamage = Math.max(0, modifiedFallDamage);
                cir.setReturnValue(modifiedFallDamage);
            }
        }
    }
}