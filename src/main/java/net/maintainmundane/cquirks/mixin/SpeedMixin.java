package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.YourMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static net.maintainmundane.cquirks.CustomSpeedEffect.SPEED_MODIFIER_UUID;

@Mixin(PlayerEntity.class)
public class SpeedMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void adjustSpeed(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        int totalLevel = 0;

        for (ItemStack armor : player.getArmorItems()) {
            if (EnchantmentHelper.get(armor).containsKey(YourMod.SWIFT_ENCHANTMENT)) {
                totalLevel += EnchantmentHelper.getLevel(YourMod.SWIFT_ENCHANTMENT, armor);
            }
        }

        if (totalLevel > 0) {
            player.addStatusEffect(new StatusEffectInstance(YourMod.CUSTOM_SPEED_EFFECT, 20, totalLevel - 1, true, false, false));
        } else if (player.getStatusEffect(YourMod.CUSTOM_SPEED_EFFECT) != null) {
            player.removeStatusEffect(YourMod.CUSTOM_SPEED_EFFECT);
            if (Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getModifier(SPEED_MODIFIER_UUID) != null) {
                Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).removeModifier(SPEED_MODIFIER_UUID);
            }
        }
    }
}
