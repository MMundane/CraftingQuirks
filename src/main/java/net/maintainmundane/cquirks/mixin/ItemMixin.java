package net.maintainmundane.cquirks.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (stack.hasNbt()) {
            assert stack.getNbt() != null;
            if (stack.getNbt().contains("CraftEffectTooltip")) {
                String effectTooltip = stack.getNbt().getString("CraftEffectTooltip");
                Text text = Text.of(effectTooltip);
                Style greenStyle = Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN));
                Text styledText = Texts.bracketed(text).setStyle(greenStyle);
                tooltip.add(styledText);
            }
        }
    }
}

