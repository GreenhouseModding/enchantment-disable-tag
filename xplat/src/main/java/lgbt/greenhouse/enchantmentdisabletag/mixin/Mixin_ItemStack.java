package lgbt.greenhouse.enchantmentdisabletag.mixin;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("DeprecatedIsStillUsed")
@Mixin(ItemStack.class)
public abstract class Mixin_ItemStack implements Duck_PotentialEnchantmentDisabledStack {
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

    @Unique
    private boolean enchantmentdisabletag$wasDisabled = false;
    @Unique
    private boolean enchantmentdisabletag$changedToUnenchantedItem = false;

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void enchantmentdisabletag$removeDisabledEnchantmentsFromTag(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag workingTag = compoundTag.getCompound("tag");
        boolean result = EnchantmentDisableTag.removeDisabledEnchantments(workingTag);
        if (result) {
            enchantmentdisabletag$wasDisabled = true;
            if (getItem().equals(Items.ENCHANTED_BOOK) && !workingTag.contains("StoredEnchantments")) {
                item = Items.BOOK;
                enchantmentdisabletag$changedToUnenchantedItem = true;
            }
            tag = workingTag;
            getItem().verifyTagAfterLoad(tag);
        }
    }

    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
    private void enchantmentdisabletag$disableEnchantingStackDirectly(Enchantment enchantment, int level, CallbackInfo ci) {
        var holder = EnchantmentDisableTag.getHelper().getHolder(enchantment);
        if (holder.isPresent() && holder.get().is(EnchantmentDisableTag.DISABLED)) {
            enchantmentdisabletag$wasDisabled = true;
            ci.cancel();
        }
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
