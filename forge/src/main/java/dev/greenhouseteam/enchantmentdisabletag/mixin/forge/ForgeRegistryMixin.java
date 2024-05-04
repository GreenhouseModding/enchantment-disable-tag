package dev.greenhouseteam.enchantmentdisabletag.mixin.forge;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Thanks Forge!
@Mixin(value = ForgeRegistry.class, remap = false)
public class ForgeRegistryMixin<V> {
    @ModifyReturnValue(method = "getKeys", at = @At("RETURN"))
    private @NotNull Set<ResourceLocation> enchantmentdisabletag$filterOutKeys(@NotNull Set<ResourceLocation> original) {
        if ((ForgeRegistry<V>)(Object)this != ForgeRegistries.ENCHANTMENTS || EnchantmentDisableTag.getHelper() == null)
            return original;
        return original.stream().filter(resourceLocation -> EnchantmentDisableTag.getHelper().getHolder(resourceLocation).map(holder -> !holder.is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG)).orElse(true)).collect(Collectors.toUnmodifiableSet());
    }

    @ModifyReturnValue(method = "getResourceKeys", at = @At("RETURN"))
    private Set<ResourceKey<V>> enchantmentdisabletag$filterOutResourceKeys(@NotNull Set<ResourceKey<V>> original) {
        if ((ForgeRegistry<V>)(Object)this != ForgeRegistries.ENCHANTMENTS || EnchantmentDisableTag.getHelper() == null)
            return original;
        return original.stream().filter(key -> EnchantmentDisableTag.getHelper().getHolder((ResourceKey<Enchantment>) key).map(holder -> !holder.is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG)).orElse(true)).collect(Collectors.toUnmodifiableSet());
    }

    @ModifyReturnValue(method = "getValues", at = @At("RETURN"))
    private Collection<V> enchantmentdisabletag$filterOutValues(@NotNull Collection<V> original) {
        if ((ForgeRegistry<V>)(Object)this != ForgeRegistries.ENCHANTMENTS || EnchantmentDisableTag.getHelper() == null)
            return original;
        return original.stream().filter(value -> EnchantmentDisableTag.getHelper().getHolder((Enchantment) value).map(holder -> !holder.is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG)).orElse(true)).collect(Collectors.toUnmodifiableSet());
    }

    @ModifyReturnValue(method = "getEntries", at = @At("RETURN"))
    private Set<Map.Entry<ResourceKey<V>, V>> entries(@NotNull Set<Map.Entry<ResourceKey<V>, V>> original) {
        if ((ForgeRegistry<V>)(Object)this != ForgeRegistries.ENCHANTMENTS || EnchantmentDisableTag.getHelper() == null)
            return original;
        return original.stream().filter(value -> EnchantmentDisableTag.getHelper().getHolder((ResourceKey<Enchantment>) value.getKey()).map(holder -> !holder.is(EnchantmentDisableTag.DISABLED_ENCHANTMENT_TAG)).orElse(true)).collect(Collectors.toUnmodifiableSet());
    }
}
