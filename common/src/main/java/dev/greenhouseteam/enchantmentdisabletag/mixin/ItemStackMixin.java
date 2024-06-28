package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Mutable
    @Shadow @Final @Deprecated @Nullable
    private Item item;

    @Shadow public abstract boolean is(Item item);

    @Mutable
    @Shadow @Final private PatchedDataComponentMap components;

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At("TAIL"))
    private void enchantmentdisabletag$unenchantBookIfDisabledInit(ItemLike item, int count, PatchedDataComponentMap map, CallbackInfo ci) {
        if (EnchantmentDisableTag.shouldSetToBookAndResetState() && this.is(Items.ENCHANTED_BOOK)) {
            this.item = Items.BOOK;
            var components = new PatchedDataComponentMap(item.asItem().components());
            components.applyPatch(map.asPatch());
            this.components = components;
        }
    }

    @Inject(method = "applyComponentsAndValidate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;validateStrict(Lnet/minecraft/world/item/ItemStack;)Lcom/mojang/serialization/DataResult;"))
    private void enchantmentdisabletag$unenchantBookIfAllDisabledAndValidate(DataComponentPatch patch, CallbackInfo ci) {
        if (EnchantmentDisableTag.shouldSetToBookAndResetState() && this.is(Items.ENCHANTED_BOOK)) {
            this.item = Items.BOOK;
            var components = new PatchedDataComponentMap(item.components());
            components.applyPatch(this.components.asPatch());
            this.components = components;
        }
    }

    @Inject(method = "applyComponents(Lnet/minecraft/core/component/DataComponentPatch;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;verifyComponentsAfterLoad(Lnet/minecraft/world/item/ItemStack;)V"))
    private void enchantmentdisabletag$unenchantBookIfAllDisabled(DataComponentPatch patch, CallbackInfo ci) {
        if (EnchantmentDisableTag.shouldSetToBookAndResetState() && this.is(Items.ENCHANTED_BOOK)) {
            this.item = Items.BOOK;
            var components = new PatchedDataComponentMap(item.components());
            components.applyPatch(this.components.asPatch());
            this.components = components;
        }
    }
}
