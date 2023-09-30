package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.Replenishing;
import net.maintainmundane.cquirks.YourMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ReplenishingMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.handSwinging) {
            return;
        }
        player.getInventory().main.forEach(itemStack -> {
            if (EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.REPLENISHING), itemStack) > 0) {
                int level = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.REPLENISHING), itemStack);
                int ticks = itemStack.getOrCreateNbt().getInt("ReplenishTicks");

                if (ticks > 0) {
                    ticks--;
                } else {
                    if (itemStack.isDamageable()) {
                        int currentDamage = itemStack.getDamage();
                        if (currentDamage > 0) {
                            itemStack.setDamage(currentDamage - 1);
                        }
                    }
                    ticks = Replenishing.getRecoveryTime(level);
                }

                itemStack.getOrCreateNbt().putInt("ReplenishTicks", ticks);
            }
        });
    }
}