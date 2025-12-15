package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.util.Either;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_DisableTagSyncContext;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = { HolderSet.Direct.class, HolderSet.Named.class })
public class Mixin_HolderSet<T> implements Duck_DisableTagSyncContext {
    @Unique
    private boolean enchantmentdisabletag$syncing = false;

    @SuppressWarnings({"unchecked", "ConstantValue"})
    @ModifyReturnValue(method = "contents", at = @At("RETURN"))
    private List<Holder<T>> enchantmentdisabletag$disableObtainingFromHolderSets(List<Holder<T>> original) {
        if ((HolderSet<T>)this instanceof HolderSet.Named<T> named && named.key().equals(EnchantmentDisableTag.DISABLED) || enchantmentdisabletag$syncing || original == null) {
            return original;
        }

        return original.stream()
                .filter(holder -> {
                    if (!holder.isBound() || !(holder.value() instanceof Enchantment))
                        return true;

                    try {
                        return !((Holder<Enchantment>)holder).is(EnchantmentDisableTag.DISABLED);
                    } catch (IllegalStateException ex) {
                        return true;
                    }
                })
                .toList();
    }

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "unwrap", at = @At("RETURN"))
    private Either<TagKey<T>, List<Holder<T>>> enchantmentdisabletag$disableFromHolderSetDirectUnwrap(Either<TagKey<T>, List<Holder<T>>> original) {
        if ((HolderSet<T>)this instanceof HolderSet.Named<T>) {
            return original;
        }

        return original.mapRight(holders -> holders.stream()
                .filter(holder -> {
                    if (!holder.isBound() || !(holder.value() instanceof Enchantment))
                        return true;

                    try {
                        return !((Holder<Enchantment>)holder).is(EnchantmentDisableTag.DISABLED);
                    } catch (IllegalStateException ex) {
                        return true;
                    }
                })
                .toList());
    }

    @Override
    public void enchantmentdisabletag$setSyncing(boolean value) {
        enchantmentdisabletag$syncing = value;
    }
}
