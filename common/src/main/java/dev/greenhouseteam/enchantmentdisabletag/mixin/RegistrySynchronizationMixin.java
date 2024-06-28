package dev.greenhouseteam.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(RegistrySynchronization.class)
public class RegistrySynchronizationMixin {
    @ModifyExpressionValue(method = { "method_56596", "lambda$packRegistry$3" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;holders()Ljava/util/stream/Stream;"))
    private static <T> Stream<Holder.Reference<T>> enchantmentdisabletag$packWithDisabledEnchantments(Stream<Holder.Reference<T>> original, @Local(argsOnly = true) Registry<T> registry) {
        if (registry.key() == (ResourceKey<? extends Registry<?>>) Registries.ENCHANTMENT)
            // This is hacky, but it's the only way we can seemingly sync the values to the client properly while also allowing for /reload to work.
            return ((MappedRegistryAccessor<T>)registry).enchantmentdisabletag$getById().stream();
        return original;
    }
}
