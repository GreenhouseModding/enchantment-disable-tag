package lgbt.greenhouse.enchantmentdisabletag;

import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import lgbt.greenhouse.enchantmentdisabletag.platform.EnchantmentDisableTagPlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Optional;

@SuppressWarnings("DataFlowIssue")
public class EnchantmentDisableTag {
    public static final String MOD_ID = "enchantmentdisabletag";
    public static final TagKey<Enchantment> DISABLED = TagKey.create(Registries.ENCHANTMENT, EnchantmentDisableTag.id("disabled"));

    private static EnchantmentDisableTagPlatformHelper helper;
    private static boolean creativeTabReload = false;

    public static ItemStack removeDisabledEnchantments(ItemStack stack) {
        if (stack.getTag() == null)
            return stack;

        CompoundTag tag = stack.getTag();
        if (removeDisabledEnchantments(tag)) {
            ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$setWasDisabled();
            if (stack.is(Items.ENCHANTED_BOOK) && !tag.contains("StoredEnchantments")) {
                ItemStack book = new ItemStack(Items.BOOK, stack.getCount());
                book.setTag(tag);
                ((Duck_PotentialEnchantmentDisabledStack)(Object)book).enchantmentdisabletag$setWasDisabled();
                ((Duck_PotentialEnchantmentDisabledStack)(Object)book).enchantmentdisabletag$setChangedToUnenchantedItem();
                return book;
            }
        }
        return stack;
    }

    public static boolean removeDisabledEnchantments(CompoundTag tag) {
        if (tag.contains("Enchantments", Tag.TAG_LIST) && removeDisabledEnchantments(tag.getList("Enchantments", Tag.TAG_COMPOUND))) {
            if (tag.getList("Enchantments", Tag.TAG_COMPOUND).isEmpty()) {
                tag.remove("Enchantments");
            }
            return true;
        }
        if (tag.contains("StoredEnchantments", Tag.TAG_LIST) && removeDisabledEnchantments(tag.getList("StoredEnchantments", Tag.TAG_COMPOUND))) {
            if (tag.getList("StoredEnchantments", Tag.TAG_COMPOUND).isEmpty()) {
                tag.remove("StoredEnchantments");
            }
            return true;
        }
        return false;
    }

    public static boolean removeDisabledEnchantments(ListTag list) {
        if (list == null)
            return false;
        return list.removeIf(tag -> {
            if (!(tag instanceof CompoundTag compoundTag))
                return false;
            if (compoundTag.contains("id", Tag.TAG_STRING)) {
                Optional<Holder<Enchantment>> enchantmentHolder = EnchantmentDisableTag.getHelper().getHolder(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(compoundTag.getString("id"))));
                return enchantmentHolder.isPresent() && enchantmentHolder.get().is(DISABLED);
            }
            return false;
        });
    }

    public static EnchantmentDisableTagPlatformHelper getHelper() {
        if (helper == null) {
            helper = EnchantmentDisableTagPlatformHelper.load();
        }
        return helper;
    }


    public static boolean getAndResetCreativeTabReloadState() {
        boolean retValue = creativeTabReload;
        creativeTabReload = false;
        return retValue;
    }

    public static void setCreativeTabToReload() {
        creativeTabReload = true;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}