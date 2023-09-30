package net.maintainmundane.cquirks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Random;

public class Windwalker extends Enchantment implements CraftEffect {
    private static final Random RANDOM = new Random();

    public Windwalker() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem;
    }

    @Override
    public void applyEffect(ItemStack stack) {
        int level = RANDOM.nextInt(3) + 1;

        stack.addEnchantment(this, level);
        stack.getOrCreateNbt().putString("CraftEffectTooltip", "Windwalker " + level);
    }

    @Override
    public String getTooltip() {
        return "Windwalker";
    }

    public static void checkAllPlayersForWindwalker(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            Windwalker.checkWindwalkerEnchantment(player);
        }
    }
    public static void checkWindwalkerEnchantment(ServerPlayerEntity player) {
        
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack armorStack = player.getEquippedStack(slot);
            int level = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(YourMod.WINDWALKER), armorStack);
            if (level > 0) {
                applyJumpBoostEffect(player, level);
                break; 
            }
        }
    }

    public static void applyJumpBoostEffect(ServerPlayerEntity player, int level) {
        if (level > 0) {
            player.addStatusEffect(new StatusEffectInstance(YourMod.HIDDEN_JUMP_BOOST, Integer.MAX_VALUE, level - 1, false, false));
        }
    }
}