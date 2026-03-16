package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.core.Holder;
import net.minecraft.core.component.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
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

    @Shadow
    @Final
    @Mutable
    private PatchedDataComponentMap components;

    @Shadow
    public abstract Item getItem();

    @Mutable
    @Shadow
    @Final
    @Deprecated
    private @org.jspecify.annotations.Nullable Holder<Item> item;
    @Unique
    private boolean enchantmentdisabletag$wasDisabled = false;
    @Unique
    private boolean enchantmentdisabletag$changedToUnenchantedItem = false;

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

    @WrapOperation(method = "set(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/component/PatchedDataComponentMap;set(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"))
    @Nullable
    private <T> T enchantmentdisabletag$removeDisabledEnchantmentsWhenSettingComponents(PatchedDataComponentMap instance, DataComponentType<? super T> component, T value, Operation<T> original) {
        if (value instanceof ItemEnchantments originalEnchantments) {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(originalEnchantments);
            mutable.removeIf(enchantmentHolder -> enchantmentHolder.is(EnchantmentDisableTag.DISABLED));
            ItemEnchantments newEnchantments = mutable.toImmutable();
            if (!newEnchantments.equals(originalEnchantments)) {
                if (newEnchantments.isEmpty() && component == DataComponents.STORED_ENCHANTMENTS && ((ItemInstance)this).is(Items.ENCHANTED_BOOK)) {
                    item = Items.BOOK.builtInRegistryHolder();
                    components = PatchedDataComponentMap.fromPatch(item.components(), components.asPatch());
                    enchantmentdisabletag$changedToUnenchantedItem = true;
                }
                enchantmentdisabletag$wasDisabled = true;
                return original.call(instance, component, newEnchantments);
            }
        }
        return original.call(instance, component, value);
    }

    @WrapOperation(method = "set(Lnet/minecraft/core/component/TypedDataComponent;)Ljava/lang/Object;", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/component/PatchedDataComponentMap;set(Lnet/minecraft/core/component/TypedDataComponent;)Ljava/lang/Object;"))
    @Nullable
    private <T> T enchantmentdisabletag$removeDisabledEnchantmentsWhenSettingComponents(PatchedDataComponentMap instance, TypedDataComponent<T> typedComponent, Operation<T> original) {
        if (typedComponent.value() instanceof ItemEnchantments originalEnchantments) {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(originalEnchantments);
            mutable.removeIf(enchantmentHolder -> enchantmentHolder.is(EnchantmentDisableTag.DISABLED));
            ItemEnchantments newEnchantments = mutable.toImmutable();
            if (!newEnchantments.equals(originalEnchantments)) {
                if (newEnchantments.isEmpty() && typedComponent.type() == DataComponents.STORED_ENCHANTMENTS && ((ItemInstance)this).is(Items.ENCHANTED_BOOK)) {
                    item = Items.BOOK.builtInRegistryHolder();
                    components = PatchedDataComponentMap.fromPatch(getItem().components(), components.asPatch());
                    enchantmentdisabletag$changedToUnenchantedItem = true;
                }
                enchantmentdisabletag$wasDisabled = true;
                //noinspection unchecked
                return original.call(instance, new TypedDataComponent<>((DataComponentType<? super ItemEnchantments>)(Object)typedComponent.type(), newEnchantments));
            }
        }
        return original.call(instance, typedComponent);
    }

    @Override
    public boolean enchantmentdisabletag$wasDisabled() {
        return enchantmentdisabletag$wasDisabled;
    }

    @Override
    public void enchantmentdisabletag$setWasDisabled() {
        enchantmentdisabletag$wasDisabled = true;
    }

    @Override
    public boolean enchantmentdisabletag$changedToUnenchantedItem() {
        return enchantmentdisabletag$changedToUnenchantedItem;
    }
}
