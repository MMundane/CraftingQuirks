package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.Unbreakable;
import net.maintainmundane.cquirks.YourMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class UnbreakableMixin {
    @Unique
    private static final Random RANDOM = new Random();

    @Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    private <T extends LivingEntity> void injectCustomUnbreaking(int amount, T entity, Consumer<T> breakCallback, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        int level = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.UNBREAKABLE), stack);

        if (level > 0) {
            float chance = Unbreakable.getDamageReduction(level);
            if (RANDOM.nextFloat() < chance) {
                ci.cancel();  
            }
        }
    }
}
