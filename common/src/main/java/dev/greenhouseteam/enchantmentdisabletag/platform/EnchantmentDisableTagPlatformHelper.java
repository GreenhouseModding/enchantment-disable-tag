package dev.greenhouseteam.enchantmentdisabletag.platform;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface EnchantmentDisableTagPlatformHelper {
    Optional<Holder<Enchantment>> getHolder(Enchantment enchantment);
    Optional<Holder<Enchantment>> getHolder(ResourceLocation enchantmentId);
    Optional<Holder<Enchantment>> getHolder(ResourceKey<Enchantment> enchantmentKey);
}
