package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntList;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_DisableTagSyncContext;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagNetworkSerialization;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(TagNetworkSerialization.class)
public class Mixin_TagNetworkSerialization {
    @Inject(method = { "method_40103", "lambda$serializeToNetwork$2" }, at = @At("HEAD"))
    private static <T> void enchantmentdisabletag$setToSyncWhilstSerializingTagsToNetwork(Registry<T> registry,
                                                                                          Map<ResourceLocation, IntList> map,
                                                                                          Pair<TagKey<T>, HolderSet.Named<T>> pair,
                                                                                          CallbackInfo ci) {
        ((Duck_DisableTagSyncContext)pair.getSecond()).enchantmentdisabletag$setSyncing(true);
    }

    @Inject(method = { "method_40103", "lambda$serializeToNetwork$2" }, at = @At("TAIL"))
    private static <T> void enchantmentdisabletag$resetSyncStateAfterSerializingTagsToNetwork(Registry<T> registry,
                                                                                              Map<ResourceLocation, IntList> map,
                                                                                              Pair<TagKey<T>, HolderSet.Named<T>> pair,
                                                                                              CallbackInfo ci) {
        ((Duck_DisableTagSyncContext)pair.getSecond()).enchantmentdisabletag$setSyncing(false);
    }
}
