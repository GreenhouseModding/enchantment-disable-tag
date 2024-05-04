package dev.greenhouseteam.enchantmentdisabletag.platform;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EnchantmentDisableTagHelperForge implements EnchantmentDisableTagPlatformHelper {
    @Override
    public Optional<Holder<Enchantment>> getHolder(Enchantment enchantment) {
        return ForgeRegistries.ENCHANTMENTS.getHolder(enchantment);
    }

    @Override
    public Optional<Holder<Enchantment>> getHolder(ResourceLocation enchantmentId) {
        return ForgeRegistries.ENCHANTMENTS.getHolder(enchantmentId);
    }

    @Override
    public @NotNull Optional<Holder<Enchantment>> getHolder(ResourceKey<Enchantment> enchantmentKey) {
        return ForgeRegistries.ENCHANTMENTS.getHolder(enchantmentKey);
    }
}
