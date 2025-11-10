package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FriendlyByteBuf.class)
public class Mixin_FriendlyByteBuf {
    @ModifyReturnValue(method = "readItem", at = @At("RETURN"))
    private ItemStack enchantmentdisabletag$removeDisabledEnchantmentsFromReadItem(ItemStack original) {
        return EnchantmentDisableTag.removeDisabledEnchantments(original);
    }
}
