package dev.greenhouseteam.enchantmentdisabletag.mixin.fabric;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
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

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Shadow
    @Nullable
    private CompoundTag tag;

    @Mutable @Shadow @Final @Deprecated @Nullable
    private Item item;

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At(value = "TAIL"))
    private void enchantmentdisabletag$verifyEnchantmentTagsFromInit(CompoundTag compoundTag, CallbackInfo ci) {
        if (this.tag != null && EnchantmentDisableTag.removeDisabledEnchantments(this.tag) && item == Items.ENCHANTED_BOOK) {
            this.item = Items.BOOK;
        }
    }
}