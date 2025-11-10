package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.mojang.serialization.Codec;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ItemStack.class)
public abstract class Mixin_ItemStack {
    @Shadow
    @Final
    @Mutable
    public static StreamCodec<RegistryFriendlyByteBuf, ItemStack> OPTIONAL_STREAM_CODEC;

    @Shadow
    @Final
    @Mutable
    public static Codec<ItemStack> CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void enchantmentdisabletag$removeEnchantmentsWhenDecoding(CallbackInfo ci) {
        CODEC = CODEC.xmap(EnchantmentDisableTag::removeDisabledEnchantments, Function.identity());
        OPTIONAL_STREAM_CODEC = OPTIONAL_STREAM_CODEC.map(EnchantmentDisableTag::removeDisabledEnchantments, Function.identity());
    }
}
