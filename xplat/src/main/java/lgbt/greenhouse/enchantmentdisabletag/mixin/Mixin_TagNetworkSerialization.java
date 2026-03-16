package lgbt.greenhouse.enchantmentdisabletag.mixin;

import it.unimi.dsi.fastutil.ints.IntList;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_DisableTagSyncContext;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagNetworkSerialization;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(TagNetworkSerialization.class)
public class Mixin_TagNetworkSerialization {
    @Inject(method = "lambda$serializeToNetwork$0", at = @At("HEAD"))
    private static <T> void enchantmentdisabletag$setToSyncWhilstSerializingTagsToNetwork(Registry<T> registry,
                                                                                          Map<Identifier, IntList> map,
                                                                                          HolderSet.Named<T> holder,
                                                                                          CallbackInfo ci) {
        ((Duck_DisableTagSyncContext)holder).enchantmentdisabletag$setSyncing(true);
    }

    @Inject(method = "lambda$serializeToNetwork$0", at = @At("TAIL"))
    private static <T> void enchantmentdisabletag$resetSyncStateAfterSerializingTagsToNetwork(Registry<T> registry,
                                                                                              Map<Identifier, IntList> map,
                                                                                              HolderSet.Named<T> holder,
                                                                                              CallbackInfo ci) {
        ((Duck_DisableTagSyncContext)holder).enchantmentdisabletag$setSyncing(false);
    }
}
