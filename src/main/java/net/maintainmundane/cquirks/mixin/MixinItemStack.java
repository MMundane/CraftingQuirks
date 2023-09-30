package net.maintainmundane.cquirks.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.maintainmundane.cquirks.YourMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Inject(
            method = "getTooltip",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onGetTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
        if (enchantments.containsKey(YourMod.SHARPEN)) {  // Assuming you have made sharpenEnchantment public or provide a public method to access it
            // Copy the original tooltip data
            List<Text> modifiedTooltip = new ArrayList<>(cir.getReturnValue());

            // Logic to remove or alter the SHARPEN enchantment tooltip data
            // This may involve iterating over modifiedTooltip and removing or altering the relevant Text objects.
            // ...

            cir.setReturnValue(modifiedTooltip);  // Set the modified tooltip
        }
    }
}
