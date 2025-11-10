package lgbt.greenhouse.enchantmentdisabletag.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;

public class EnchantmentDisableTagTest implements ModInitializer {
    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            HolderLookup<Enchantment> enchantmentLookup = entries.getContext().holders().lookupOrThrow(Registries.ENCHANTMENT);

            ItemStack chestplateStack = new ItemStack(Items.IRON_CHESTPLATE);
            chestplateStack.enchant(enchantmentLookup.getOrThrow(Enchantments.THORNS), 3);
            entries.accept(chestplateStack);

            ItemStack swordStack = new ItemStack(Items.DIAMOND_SWORD);
            ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            enchantments.set(enchantmentLookup.getOrThrow(Enchantments.SHARPNESS), 1);
            enchantments.set(enchantmentLookup.getOrThrow(Enchantments.LOOTING), 2);
            EnchantmentHelper.setEnchantments(swordStack, enchantments.toImmutable());
            entries.accept(swordStack);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            HolderLookup<Enchantment> enchantmentLookup = entries.getContext().holders().lookupOrThrow(Registries.ENCHANTMENT);
            entries.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantmentLookup.getOrThrow(Enchantments.AQUA_AFFINITY), 1)));
        });
    }
}
