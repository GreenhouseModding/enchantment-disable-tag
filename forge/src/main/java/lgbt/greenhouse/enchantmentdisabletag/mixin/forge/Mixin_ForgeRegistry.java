package lgbt.greenhouse.enchantmentdisabletag.mixin.forge;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.*;
import java.util.stream.Collectors;

// Thanks Forge!
// Lex would call me some foul words for this one.
@SuppressWarnings("UnstableApiUsage")
@Mixin(ForgeRegistry.class)
public abstract class Mixin_ForgeRegistry<V> {
    @Shadow @Final private ResourceKey<Registry<V>> key;

    @Shadow
    @NotNull
    public abstract Optional<Holder<V>> getHolder(V value);

    @ModifyReturnValue(method = "getKeys", at = @At("RETURN"), remap = false)
    private @NotNull Set<ResourceLocation> enchantmentdisabletag$filterOutKeys(@NotNull Set<ResourceLocation> original) {
        if (!key.equals(Registries.ENCHANTMENT))
            return original;
        return original.stream().filter(resourceLocation -> EnchantmentDisableTag.getHelper().
                getHolder(resourceLocation)
                .map(holder -> !holder.is(EnchantmentDisableTag.DISABLED))
                .orElse(true)
        ).collect(Collectors.toUnmodifiableSet());
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "getResourceKeys", at = @At("RETURN"), remap = false)
    private Set<ResourceKey<V>> enchantmentdisabletag$filterOutResourceKeys(@NotNull Set<ResourceKey<V>> original) {
        if (!key.equals(Registries.ENCHANTMENT))
            return original;
        return original.stream().filter(key -> EnchantmentDisableTag.getHelper()
                .getHolder((ResourceKey<Enchantment>) key).
                map(holder -> !holder.is(EnchantmentDisableTag.DISABLED))
                .orElse(true)
        ).collect(Collectors.toUnmodifiableSet());
    }

    @ModifyReturnValue(method = "getValues", at = @At("RETURN"), remap = false)
    private Collection<V> enchantmentdisabletag$filterOutValues(@NotNull Collection<V> original) {
        if (!key.equals(Registries.ENCHANTMENT))
            return original;
        return original.stream().filter(value -> EnchantmentDisableTag.getHelper()
                .getHolder((Enchantment) value)
                .map(holder -> !holder.is(EnchantmentDisableTag.DISABLED))
                .orElse(true)
        ).collect(Collectors.toUnmodifiableSet());
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "getEntries", at = @At("RETURN"), remap = false)
    private Set<Map.Entry<ResourceKey<V>, V>> enchantmentdisabletag$filterEntries(@NotNull Set<Map.Entry<ResourceKey<V>, V>> original) {
        if (!key.equals(Registries.ENCHANTMENT))
            return original;
        return original.stream().filter(value -> EnchantmentDisableTag.getHelper()
                .getHolder((ResourceKey<Enchantment>) value.getKey())
                .map(holder -> !holder.is(EnchantmentDisableTag.DISABLED))
                .orElse(true)
        ).collect(Collectors.toUnmodifiableSet());
    }

    @SuppressWarnings({"unchecked"})
    @ModifyReturnValue(method = "iterator", at = @At("RETURN"), remap = false)
    private Iterator<V> enchantmentdisabletag$filterIterator(Iterator<V> original) {
        if (!key.equals(Registries.ENCHANTMENT))
            return original;

        // This is less performant, but we do it this way just in case new values are put into the iterator.
        List<V> list = new ArrayList<>();
        while (original.hasNext()) {
            V it = original.next();
            Optional<Holder<V>> holder = getHolder(it);
            if (holder.isPresent() && !((Holder<Enchantment>)holder.get()).is(EnchantmentDisableTag.DISABLED)) {
                list.add(it);
            }
        }
        return list.iterator();
    }
}
