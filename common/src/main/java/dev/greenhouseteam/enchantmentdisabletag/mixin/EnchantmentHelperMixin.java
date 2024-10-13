package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
import dev.greenhouseteam.enchantmentdisabletag.access.ItemEnchantmentsMutableAccess;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "updateEnchantments", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    private static void enchantmentdisabletag$filterOutDisabledEnchantments(ItemStack stack, Consumer<ItemEnchantments.Mutable> consumer, CallbackInfoReturnable<ItemEnchantments> cir, @Local ItemEnchantments.Mutable mutable) {
        ((ItemEnchantmentsMutableAccess)mutable).enchantmentdisabletag$setToValidate();
    }

    @ModifyExpressionValue(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"))
    private static Stream<Holder<Enchantment>> enchantmentdisabletag$filterOutDisabledEnchantmentsInSelectEnchantment(Stream<Holder<Enchantment>> original) {
        return original.filter(it -> !it.is(EnchantmentDisableTags.DISABLED));
    }
}
