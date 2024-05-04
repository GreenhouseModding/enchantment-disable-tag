package dev.greenhouseteam.enchantmentdisabletag.mixin.fabric.client;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(method = "handleUpdateTags", at = @At("TAIL"))
    private void enchantmentdisabletag$updateTagValue(ClientboundUpdateTagsPacket packet, CallbackInfo ci) {
        EnchantmentDisableTag.setReloaded();
    }
}
