package lgbt.greenhouse.enchantmentdisabletag.platform;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public class EnchantmentDisableTagPlatformHelperFabric implements EnchantmentDisableTagPlatformHelper {
    @Override
    public Optional<Holder<Enchantment>> getHolder(Enchantment enchantment) {
        return Optional.of(BuiltInRegistries.ENCHANTMENT.wrapAsHolder(enchantment));
    }

    @Override
    public Optional<Holder<Enchantment>> getHolder(ResourceLocation enchantmentId) {
        return BuiltInRegistries.ENCHANTMENT.getHolder(ResourceKey.create(Registries.ENCHANTMENT, enchantmentId)).map(Function.identity());
    }

    @Override
    public @NotNull Optional<Holder<Enchantment>> getHolder(ResourceKey<Enchantment> enchantmentKey) {
        return BuiltInRegistries.ENCHANTMENT.getHolder(enchantmentKey).map(Function.identity());
    }

    @Override
    public Class<? extends EnchantmentDisableTagPlatformHelper> type() {
        return EnchantmentDisableTagPlatformHelperFabric.class;
    }

    @Override
    public EnchantmentDisableTagPlatformHelper get() {
        return this;
    }
}
