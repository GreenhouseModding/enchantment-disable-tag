package com.example.examplemod.mixin;

import com.example.examplemod.access.ItemEnchantmentsAccess;
import com.example.examplemod.access.ItemEnchantmentsMutableAccess;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ItemEnchantments.Mutable.class)
public class ItemEnchantmentsMutableMixin implements ItemEnchantmentsMutableAccess {
    @Unique
    private boolean enchantmentdisabletag$shouldValidate = false;

    @ModifyReturnValue(method = "toImmutable", at = @At("RETURN"))
    private ItemEnchantments enchantmentdisabletag$validateMutableEnchantments(ItemEnchantments original) {
        // If we don't do this, it will break certain methods of adding innate enchantments, which we obviously don't want.
        if (enchantmentdisabletag$shouldValidate())
            ((ItemEnchantmentsAccess)original).enchantmentdisabletag$validate();
        return original;
    }

    @Override
    public boolean enchantmentdisabletag$shouldValidate() {
        return enchantmentdisabletag$shouldValidate;
    }

    @Override
    public void enchantmentdisabletag$setToValidate() {
        enchantmentdisabletag$shouldValidate = true;
    }
}
