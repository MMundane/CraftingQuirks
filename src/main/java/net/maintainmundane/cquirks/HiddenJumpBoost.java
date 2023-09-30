package net.maintainmundane.cquirks;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class HiddenJumpBoost extends StatusEffect {
    public static final StatusEffectInstance INSTANCE = new StatusEffectInstance(new HiddenJumpBoost(), Integer.MAX_VALUE, 0, false, false);

    public HiddenJumpBoost() {
        super(StatusEffectCategory.BENEFICIAL, 0xffffff); 
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
        
        return 3.5 * amplifier; 
    }
}
