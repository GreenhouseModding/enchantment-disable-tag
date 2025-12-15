package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.ObjectImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectList;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(MappedRegistry.class)
public abstract class Mixin_MappedRegistry<T> implements Duck_DisableTagSyncContext {
    @Unique
    private boolean enchantmentdisabletag$syncing = false;

    @Shadow
    public abstract Optional<Holder.Reference<T>> get(ResourceLocation p_316743_);

    @Shadow
    public abstract Optional<Holder.Reference<T>> get(ResourceKey<T> p_205905_);

    @Shadow
    @Final
    private ResourceKey<? extends Registry<T>> key;

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "keySet", at = @At(value = "INVOKE", target = "Ljava/util/Collections;unmodifiableSet(Ljava/util/Set;)Ljava/util/Set;"))
    private Set<ResourceLocation> enchantmentdisabletag$disableFromKeySet(Set<ResourceLocation> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        return original.stream().filter(t -> {
            var optionalHolder = get(t);
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
            var optionalHolder = get(t);
            return optionalHolder.isEmpty() || !optionalHolder.get().is((TagKey<T>) EnchantmentDisableTag.DISABLED);
        }).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "entrySet", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;mapValuesLazy(Ljava/util/Map;Lcom/google/common/base/Function;)Ljava/util/Map;"), index = 0)
    private Map<ResourceKey<T>, Holder.Reference<T>> enchantmentdisabletag$disableFromEntrySet(Map<ResourceKey<T>, Holder.Reference<T>> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        return original.entrySet().stream().filter(entry -> entry.getValue().is((TagKey<T>) EnchantmentDisableTag.DISABLED)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "listElements", at = @At("RETURN"))
    private Stream<Holder.Reference<T>> enchantmentdisabletag$disableFromListElements(Stream<Holder.Reference<T>> original) {
        if (enchantmentdisabletag$syncing || !key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        return original.filter(entry -> !entry.is((TagKey<T>) EnchantmentDisableTag.DISABLED));
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "iterator", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Iterators;transform(Ljava/util/Iterator;Lcom/google/common/base/Function;)Ljava/util/Iterator;"))
    private Iterator<T> enchantmentdisabletag$disableFromIterator(Iterator<T> original) {
        if (!key.equals(Registries.ENCHANTMENT)) {
            return original;
        }

        // This is less performant, but we do it this way just in case new values are put into the iterator.
        List<T> list = new ArrayList<>();
        while (original.hasNext()) {
            T it = original.next();
            if (it instanceof Holder.Reference<?> reference && reference.key().isFor(Registries.ENCHANTMENT) && !((Holder.Reference<T>)reference).is((TagKey<T>) EnchantmentDisableTag.DISABLED)) {
                list.add(it);
            }
        }
        return list.iterator();
    }

    @SuppressWarnings("unchecked")
    @ModifyExpressionValue(method = "getAny", at = @At(value = "FIELD", target = "Lnet/minecraft/core/MappedRegistry;byId:Lit/unimi/dsi/fastutil/objects/ObjectList;", ordinal = 1))
    private ObjectList<Holder.Reference<T>> enchantmentdisabletag$disableFromGetAny(ObjectList<Holder.Reference<T>> selections) {
        if (!key.equals(Registries.ENCHANTMENT) || enchantmentdisabletag$syncing) {
            return selections;
        }

        // ref can be null on NeoForge. No clue how it happens, but hey, we have to compensate sometimes.
        return selections.stream().filter(ref -> ref != null && (!ref.key().isFor(Registries.ENCHANTMENT) || !ref.is((TagKey<T>) EnchantmentDisableTag.DISABLED))).collect(ObjectImmutableList.toList());
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "getRandom", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getRandomSafe(Ljava/util/List;Lnet/minecraft/util/RandomSource;)Ljava/util/Optional;"), index = 0)
    private List<Holder.Reference<T>> enchantmentdisabletag$disableFromGetRandom(List<Holder.Reference<T>> selections) {
        if (!key.equals(Registries.ENCHANTMENT) || enchantmentdisabletag$syncing) {
            return selections;
        }

        // ref can be null on NeoForge. No clue how it happens, but hey, we have to compensate sometimes.
        return selections.stream().filter(ref -> ref != null && (!ref.key().isFor(Registries.ENCHANTMENT) || !ref.is((TagKey<T>) EnchantmentDisableTag.DISABLED))).toList();
    }

    @Override
    public void enchantmentdisabletag$setSyncing(boolean value) {
        enchantmentdisabletag$syncing = value;
    }
}
