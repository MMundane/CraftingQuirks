package net.maintainmundane.cquirks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class YourMod implements ModInitializer {
	public static final Identifier UNBREAKABLE = new Identifier("cquirks", "unbreakable");
	private static final Identifier SWIFT = new Identifier("cquirks", "swift");
	public static final Identifier FREEZING = new Identifier("cquirks", "freezing");
	public static final Identifier VENOMOUS = new Identifier("cquirks", "venomous");
	public static final Identifier LEECHING = new Identifier("cquirks", "leeching");
	public static final Identifier WARDING = new Identifier("cquirks", "warding");
	public static final Identifier REPLENISHING = new Identifier("cquirks", "replenishing");
	public static final Identifier SEARING = new Identifier("cquirks", "searing");
	public static final Identifier WINDWALKER = new Identifier("cquirks", "windwalker");
	public static final Identifier ZEN = new Identifier("cquirks", "zen");
	public static final Identifier SHARPEN = new Identifier("cquirks", "sharp");
	public static final StatusEffect HIDDEN_JUMP_BOOST = new HiddenJumpBoost();
	public static Enchantment SWIFT_ENCHANTMENT;
	public static StatusEffect CUSTOM_SPEED_EFFECT;

	@Override
	public void onInitialize() {
		ServerTickEvents.END_SERVER_TICK.register(Windwalker::checkAllPlayersForWindwalker);

		Registry.register(Registries.STATUS_EFFECT, new Identifier("cquirks", "hidden_jump_boost"), HIDDEN_JUMP_BOOST);

		Unbreakable unbreakableEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				UNBREAKABLE,
				new Unbreakable()
		);
		Swift swiftEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				SWIFT,
				new Swift()
		);
		SWIFT_ENCHANTMENT = swiftEnchantment;

		Freezing freezingEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				FREEZING,
				new Freezing()
		);

		CUSTOM_SPEED_EFFECT = Registry.register(
				Registries.STATUS_EFFECT,
				new Identifier("cquirks", "custom_speed_effect"),
				new CustomSpeedEffect()
		);
		Venomous venomousEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				VENOMOUS,
				new Venomous()
		);
		Leeching leechingEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				LEECHING,
				new Leeching()
		);
		Warding wardingEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				WARDING,
				new Warding()
		);
		Replenishing replenishingEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				REPLENISHING,
				new Replenishing()
		);
		Searing searingEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				SEARING,
				new Searing()
		);
		Windwalker windwalkerEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				WINDWALKER,
				new Windwalker()
		);
		Zen zenEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				ZEN,
				new Zen()
		);
		Sharpen sharpenEnchantment = Registry.register(
				Registries.ENCHANTMENT,
				SHARPEN,
				new Sharpen()
		);
		CraftEffectPicker.registerEffect(sharpenEnchantment);
		CraftEffectPicker.registerEffect(zenEnchantment);
		CraftEffectPicker.registerEffect(windwalkerEnchantment);
		CraftEffectPicker.registerEffect(searingEnchantment);
		CraftEffectPicker.registerEffect(unbreakableEnchantment);
		CraftEffectPicker.registerEffect(swiftEnchantment);
		CraftEffectPicker.registerEffect(freezingEnchantment);
		CraftEffectPicker.registerEffect(venomousEnchantment);
		CraftEffectPicker.registerEffect(leechingEnchantment);
		CraftEffectPicker.registerEffect(wardingEnchantment);
		CraftEffectPicker.registerEffect(replenishingEnchantment);
	}
}
