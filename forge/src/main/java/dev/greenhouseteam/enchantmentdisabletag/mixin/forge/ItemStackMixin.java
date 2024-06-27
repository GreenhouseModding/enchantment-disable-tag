package dev.greenhouseteam.enchantmentdisabletag.mixin.forge;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Shadow
    @Nullable
    private CompoundTag tag;

    @Mutable
    @Shadow @Final @Deprecated @Nullable
    private Item item;

    @Mutable
    @Shadow(remap = false) @Final @org.jetbrains.annotations.Nullable
    private Holder.@org.jetbrains.annotations.Nullable Reference<Item> delegate;

    @Inject(method = "forgeInit", at = @At(value = "HEAD"), remap = false)
    private void enchantmentdisabletag$verifyEnchantmentTagsFromInit(CallbackInfo ci) {
        if (this.tag != null && EnchantmentDisableTag.removeDisabledEnchantments(this.tag) && item == Items.ENCHANTED_BOOK) {
            this.item = Items.BOOK;
            this.delegate = ForgeRegistries.ITEMS.getDelegateOrThrow(Items.BOOK);
        }
    }
}