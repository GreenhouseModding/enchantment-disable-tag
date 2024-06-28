package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
import dev.greenhouseteam.enchantmentdisabletag.access.ItemEnchantmentsAccess;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemEnchantments.class)
public abstract class ItemEnchantmentsMixin implements ItemEnchantmentsAccess {
    @Shadow @Final
    Object2IntOpenHashMap<Holder<Enchantment>> enchantments;

    @Shadow @Mutable @Final public static Codec<ItemEnchantments> CODEC;

    @Accessor("showInTooltip")
    abstract boolean enchantmentdisabletag$getShowInTooltip();

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void enchantmentdisabletag$validateEnchantmentsInCodec(CallbackInfo ci) {
        CODEC = CODEC.flatXmap(itemEnchantments -> {
            List<Holder<Enchantment>> disabledHolders = new ArrayList<>(itemEnchantments.keySet().stream().filter(holder -> holder.is(EnchantmentDisableTags.DISABLED)).toList());
            if (!disabledHolders.isEmpty()) {
                Object2IntOpenHashMap<Holder<Enchantment>> potentialNewMap = new Object2IntOpenHashMap<>();
                for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
                    if (!entry.getKey().is(EnchantmentDisableTags.DISABLED))
                        potentialNewMap.addTo(entry.getKey(), entry.getIntValue());
                }
                var newItemEnchantments = new ItemEnchantments(potentialNewMap, ((ItemEnchantmentsMixin) (Object) itemEnchantments).enchantmentdisabletag$getShowInTooltip());
                if (!itemEnchantments.isEmpty() && newItemEnchantments.isEmpty()) {
                    newItemEnchantments = ItemEnchantments.EMPTY;
                    EnchantmentDisableTag.setBookState();
                }
                if (disabledHolders.isEmpty())
                    return DataResult.success(newItemEnchantments);
                if (disabledHolders.size() == 1) {
                    if (newItemEnchantments.isEmpty())
                        return DataResult.error(() -> "Enchantment " + disabledHolders.getFirst().getRegisteredName() + " has been disabled via the enchantmentdisabledtag:disabled enchantment tag.");
                    return DataResult.error(() -> "Enchantment " + disabledHolders.getFirst().getRegisteredName() + " has been disabled via the enchantmentdisabledtag:disabled enchantment tag.", newItemEnchantments);
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < disabledHolders.size(); ++i) {
                    if (i == disabledHolders.size() - 1)
                        builder.append("and ");
                    builder.append(disabledHolders.get(i).getRegisteredName());
                    if (i < disabledHolders.size() - 1)
                        builder.append(", ");
                }
                if (newItemEnchantments.isEmpty())
                    return DataResult.error(() -> "Enchantments " + builder.toString() + " have been disabled via the enchantmentdisabledtag:disabled enchantment tag.");
                return DataResult.error(() -> "Enchantments " + builder.toString() + " have been disabled via the enchantmentdisabledtag:disabled enchantment tag.", newItemEnchantments);
            }
            return DataResult.success(itemEnchantments);
        }, DataResult::success);
    }

    @Override
    public void enchantmentdisabletag$validate() {
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : this.enchantments.object2IntEntrySet()) {
            if (entry.getKey().is(EnchantmentDisableTags.DISABLED)) {
                this.enchantments.remove(entry, entry.getIntValue());
            }
        }
    }
}
