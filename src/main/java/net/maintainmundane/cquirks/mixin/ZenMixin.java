package net.maintainmundane.cquirks.mixin;

import net.maintainmundane.cquirks.YourMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ZenMixin {

    @Unique
    private int tickCounter = 0;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        int zenLevel = 0;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack equippedItem = player.getEquippedStack(slot);
            int level = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.ZEN), equippedItem);

            if (level > 0) {
                zenLevel = Math.max(zenLevel, level); 
            }
        }

        
        tickCounter++;

        if (zenLevel > 0 && tickCounter >= 74) {
            Vec3d velocity = player.getVelocity();
            if (velocity.x == 0 && velocity.z == 0) {
                float healAmount = 0.16F * zenLevel;
                player.heal(healAmount);

                
                tickCounter = 0;
            }
        }
    }
}
