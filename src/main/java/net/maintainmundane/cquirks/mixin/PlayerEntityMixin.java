package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.Swift;
import net.maintainmundane.cquirks.YourMod;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack mainHandStack = player.getMainHandStack();

        if (EnchantmentHelper.get(mainHandStack).containsKey(YourMod.SWIFT_ENCHANTMENT)) {
            int level = EnchantmentHelper.getLevel(YourMod.SWIFT_ENCHANTMENT, mainHandStack);
            float modifiedSpeed = cir.getReturnValue() * Swift.getSpeedIncrease(level);
            cir.setReturnValue(modifiedSpeed);
        }
    }
}