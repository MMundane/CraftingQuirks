package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.CraftEffectPicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @Inject(method = "quickMove(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void onQuickMove(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
        CraftingScreenHandler craftingScreenHandler = (CraftingScreenHandler) (Object) this;
        ItemStack stack = craftingScreenHandler.getSlot(slot).getStack();

        if (!stack.isEmpty() && CraftEffectPicker.isToolOrArmor(stack.getItem())) {
            CraftEffectPicker.applyRandomEffect(stack);
        }
    }
}
