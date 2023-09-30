package net.maintainmundane.cquirks.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(
            method = "getTooltip",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack itemStack = (ItemStack) (Object) this;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
        List<Text> originalTooltip = cir.getReturnValue();
        List<Text> modifiedTooltip = new ArrayList<>(originalTooltip);

        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Identifier enchantmentID = Registries.ENCHANTMENT.getId(entry.getKey());
            if (enchantmentID != null && enchantmentID.getNamespace().equals("cquirks")) {
                String enchantmentTranslationKey = entry.getKey().getTranslationKey();
                // The next line is assuming the `Text` interface still has a `asFormattedString` method or similar
                // Remove the tooltip line for this enchantment
                modifiedTooltip.removeIf(tooltipLine -> tooltipLine.toString().contains(Text.translatable(enchantmentTranslationKey).toString()));
            }
        }

        cir.setReturnValue(modifiedTooltip);  // Set the modified tooltip
    }
}
