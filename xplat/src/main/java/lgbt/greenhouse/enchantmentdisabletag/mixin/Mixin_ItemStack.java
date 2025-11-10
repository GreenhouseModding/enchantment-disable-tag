package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.core.component.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ItemStack.class)
public abstract class Mixin_ItemStack implements Duck_PotentialEnchantmentDisabledStack {
    @Shadow
    @Final
    @Mutable
    public static StreamCodec<RegistryFriendlyByteBuf, ItemStack> OPTIONAL_STREAM_CODEC;

    @Shadow
    @Final
    @Mutable
    public static Codec<ItemStack> CODEC;

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Mutable
    @Shadow
    @Final
    @Deprecated
    @Nullable
    private Item item;

    @Shadow
    public abstract boolean is(Item item);

    @Shadow
    public abstract DataComponentMap getComponents();

    @Unique
    private boolean enchantmentdisabletag$wasDisabled = false;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void enchantmentdisabletag$removeEnchantmentsWhenDecoding(CallbackInfo ci) {
        CODEC = CODEC.xmap(stack -> {
            ItemStack newStack = stack.copy();
            EnchantmentDisableTag.removeDisabledEnchantments(newStack);
            if (!ItemStack.isSameItemSameComponents(stack, newStack)) {
                ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$setWasDisabled();
            }
            return newStack;
        }, Function.identity());
        OPTIONAL_STREAM_CODEC = OPTIONAL_STREAM_CODEC.map(stack -> {
            ItemStack newStack = stack.copy();
            EnchantmentDisableTag.removeDisabledEnchantments(newStack);
            if (!ItemStack.isSameItemSameComponents(stack, newStack)) {
                ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$setWasDisabled();
            }
            return newStack;
        }, Function.identity());
    }

    @WrapOperation(method = "set", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/component/PatchedDataComponentMap;set(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    @Nullable
    private <T> T enchantmentdisabletag$removeDisabledEnchantmentsWhenSettingComponents(PatchedDataComponentMap instance, DataComponentType<? super T> component, T value, Operation<T> original) {
        if (value instanceof ItemEnchantments originalEnchantments) {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(originalEnchantments);
            mutable.removeIf(enchantmentHolder -> enchantmentHolder.is(EnchantmentDisableTag.DISABLED));
            ItemEnchantments newEnchantments = mutable.toImmutable();
            if (newEnchantments.isEmpty()) {
                if (component == DataComponents.STORED_ENCHANTMENTS && is(Items.ENCHANTED_BOOK)) {
                    item = Items.BOOK;
                }
                enchantmentdisabletag$wasDisabled = true;
                return original.call(instance, component, ((Accessor_PatchedDataComponentMap)getComponents()).enchantmentdisabletag$getPrototype().get(component));
            } else if (!newEnchantments.equals(originalEnchantments)) {
                enchantmentdisabletag$wasDisabled = true;
                return original.call(instance, component, newEnchantments);
            }
        }
        return original.call(instance, component, value);
    }

    @Override
    public boolean enchantmentdisabletag$wasDisabled() {
        return enchantmentdisabletag$wasDisabled;
    }

    @Override
    public void enchantmentdisabletag$setWasDisabled() {
        enchantmentdisabletag$wasDisabled = true;
    }
}
