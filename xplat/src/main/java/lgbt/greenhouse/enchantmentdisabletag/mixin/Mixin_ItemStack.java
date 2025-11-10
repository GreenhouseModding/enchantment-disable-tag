package lgbt.greenhouse.enchantmentdisabletag.mixin;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("DeprecatedIsStillUsed")
@Mixin(ItemStack.class)
public abstract class Mixin_ItemStack {
    @Shadow
    public abstract Item getItem();

    @Mutable
    @Shadow
    @Final
    @Deprecated
    @Nullable
    private Item item;

    @Shadow
    @Nullable
    private CompoundTag tag;

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void enchiridion$removeDisabledEnchantmentsFromTag(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag workingTag = compoundTag.getCompound("tag");
        boolean result = EnchantmentDisableTag.removeDisabledEnchantments(workingTag);
        if (result) {
            if (getItem().equals(Items.ENCHANTED_BOOK) && !workingTag.contains("StoredEnchantments")) {
                item = Items.BOOK;
            }
            tag = workingTag;
            getItem().verifyTagAfterLoad(tag);
        }
    }
}
