package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.YourMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class FovMixin {

    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("HEAD"), cancellable = true)
    private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (camera.getFocusedEntity() instanceof LivingEntity livingEntity) {
            StatusEffectInstance customSpeedEffect = livingEntity.getStatusEffect(YourMod.CUSTOM_SPEED_EFFECT);

            if (customSpeedEffect != null) {
                double originalFov = MinecraftClient.getInstance().options.getFov().getValue();
                cir.setReturnValue(originalFov);  
                cir.cancel();
            }
        }
    }
}
