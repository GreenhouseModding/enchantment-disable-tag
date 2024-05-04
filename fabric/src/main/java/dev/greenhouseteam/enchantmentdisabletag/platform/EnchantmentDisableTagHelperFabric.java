package dev.greenhouseteam.enchantmentdisabletag.platform;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EnchantmentDisableTagHelperFabric implements EnchantmentDisableTagPlatformHelper {
    @Override
    public Optional<Holder<Enchantment>> getHolder(Enchantment enchantment) {
        return getHolder(BuiltInRegistries.ENCHANTMENT.getKey(enchantment));
    }

    @Override
    public Optional<Holder<Enchantment>> getHolder(ResourceLocation enchantmentId) {
        return getHolder(ResourceKey.create(Registries.ENCHANTMENT, enchantmentId));
    }

    @Override
    public Optional<Holder<Enchantment>> getHolder(ResourceKey<Enchantment> enchantmentKey) {
        return BuiltInRegistries.ENCHANTMENT.getHolder(enchantmentKey).map(enchantmentReference -> enchantmentReference);
    }
}
