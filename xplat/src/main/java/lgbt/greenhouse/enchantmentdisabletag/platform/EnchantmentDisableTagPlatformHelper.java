package lgbt.greenhouse.enchantmentdisabletag.platform;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface EnchantmentDisableTagPlatformHelper extends ServiceLoader.Provider<EnchantmentDisableTagPlatformHelper>{
    Optional<Holder<Enchantment>> getHolder(Enchantment enchantment);
    Optional<Holder<Enchantment>> getHolder(ResourceLocation enchantmentId);
    @NotNull
    Optional<Holder<Enchantment>> getHolder(ResourceKey<Enchantment> enchantmentKey);

    @ApiStatus.Internal
    static EnchantmentDisableTagPlatformHelper load() {
        var loaders = ServiceLoader.load(EnchantmentDisableTagPlatformHelper.class);
        // Maintain sanity
        if (loaders.stream().findAny().isEmpty()) {
            throw new IllegalStateException("No " + EnchantmentDisableTagPlatformHelper.class.getName() + " implementation found");
        }

        return loaders
                .stream()
                .findFirst()
                .orElseThrow()
                .get();
    }
}
