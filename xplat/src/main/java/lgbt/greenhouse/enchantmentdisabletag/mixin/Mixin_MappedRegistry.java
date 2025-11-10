package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_DisableTagSyncContext;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(MappedRegistry.class)
public abstract class Mixin_MappedRegistry<T> {
    @Shadow public abstract Optional<Holder.Reference<T>> getHolder(ResourceKey<T> resourceKey);

    @Shadow
    @Final
    ResourceKey<? extends Registry<T>> key;

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "keySet", at = @At(value = "INVOKE", target = "Ljava/util/Collections;unmodifiableSet(Ljava/util/Set;)Ljava/util/Set;"))
    private Set<ResourceLocation> enchantmentdisabletag$disableFromKeySet(Set<ResourceLocation> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        return original.stream().filter(t -> {
            var optionalHolder = getHolder(ResourceKey.create(key, t));
            return optionalHolder.isEmpty() || !optionalHolder.get().is((TagKey<T>) EnchantmentDisableTag.DISABLED);
        }).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "registryKeySet", at = @At(value = "INVOKE", target = "Ljava/util/Collections;unmodifiableSet(Ljava/util/Set;)Ljava/util/Set;"))
    private Set<ResourceKey<T>> enchantmentdisabletag$disableFromRegistryKeySet(Set<ResourceKey<T>> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        return original.stream().filter(t -> {
            var optionalHolder = getHolder(t);
            return optionalHolder.isEmpty() || !optionalHolder.get().is((TagKey<T>) EnchantmentDisableTag.DISABLED);
        }).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "entrySet", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;transformValues(Ljava/util/Map;Lcom/google/common/base/Function;)Ljava/util/Map;"))
    private Map<ResourceKey<T>, Holder.Reference<T>> enchantmentdisabletag$disableFromEntrySet(Map<ResourceKey<T>, Holder.Reference<T>> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        return original.entrySet().stream()
                .filter(entry -> entry.getValue().is((TagKey<T>) EnchantmentDisableTag.DISABLED))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "holdersInOrder", at = @At("RETURN"))
    private List<Holder.Reference<T>> enchantmentdisabletag$disableFromHolders(List<Holder.Reference<T>> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        var holders = original.stream()
                .filter(ref -> ref != null && !ref.is((TagKey<T>) EnchantmentDisableTag.DISABLED))
                .toList();
        return holders;
    }
}
