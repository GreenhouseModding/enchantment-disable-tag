package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
import dev.greenhouseteam.enchantmentdisabletag.access.ItemEnchantmentsMutableAccess;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "updateEnchantments", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    private static void enchantmentdisabletag$filterOutDisabledEnchantments(ItemStack stack, Consumer<ItemEnchantments.Mutable> consumer, CallbackInfoReturnable<ItemEnchantments> cir, @Local ItemEnchantments.Mutable mutable) {
        ((ItemEnchantmentsMutableAccess)mutable).enchantmentdisabletag$setToValidate();
    }

    @Redirect(method = "selectEnchantment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getAvailableEnchantmentResults(ILnet/minecraft/world/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;"))
    private static List<EnchantmentInstance> enchantmentdisabletag$filterOutDisabledEnchantmentsInSelectEnchantment(int level, ItemStack itemStack, Stream<Holder<Enchantment>> stream) {
        var newList = stream.filter(it -> !it.is(EnchantmentDisableTags.DISABLED)).toList();
        return EnchantmentHelper.getAvailableEnchantmentResults(level, itemStack, newList.stream());
    }
}
