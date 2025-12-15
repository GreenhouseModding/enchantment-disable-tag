package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.mojang.serialization.DynamicOps;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_DisableTagSyncContext;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.repository.KnownPack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

@Mixin(RegistrySynchronization.class)
public class Mixin_RegistrySynchronization {
    @Inject(method = "packRegistry", at = @At("HEAD"))
    private static <T> void enchantmentdisabletag$setToSyncWhilstPackingEnchants(DynamicOps<Tag> ops,
                                                                                 RegistryDataLoader.RegistryData<T> registryData,
                                                                                 RegistryAccess registryAccess,
                                                                                 Set<KnownPack> packs,
                                                                                 BiConsumer<ResourceKey<? extends Registry<?>>, List<RegistrySynchronization.PackedRegistryEntry>> packetSender,
                                                                                 CallbackInfo ci) {
        if (!registryData.key().equals(Registries.ENCHANTMENT)) {
            return;
        }

        registryAccess.lookup(registryData.key())
                .ifPresent(registry -> ((Duck_DisableTagSyncContext)registry).enchantmentdisabletag$setSyncing(true));
    }

    @Inject(method = "packRegistry", at = @At("TAIL"))
    private static <T> void enchantmentdisabletag$resetSyncStateAfterPackingEnchantments(DynamicOps<Tag> ops,
                                                                                         RegistryDataLoader.RegistryData<T> registryData,
                                                                                         RegistryAccess registryAccess,
                                                                                         Set<KnownPack> packs,
                                                                                         BiConsumer<ResourceKey<? extends Registry<?>>, List<RegistrySynchronization.PackedRegistryEntry>> packetSender,
                                                                                         CallbackInfo ci) {
        if (!registryData.key().equals(Registries.ENCHANTMENT)) {
            return;
        }

        registryAccess.lookup(registryData.key())
                .ifPresent(registry -> ((Duck_DisableTagSyncContext)registry).enchantmentdisabletag$setSyncing(false));
    }
}
