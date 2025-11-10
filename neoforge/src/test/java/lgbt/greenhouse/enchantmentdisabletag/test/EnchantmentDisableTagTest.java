package lgbt.greenhouse.enchantmentdisabletag.test;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class EnchantmentDisableTagTest {
    @EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID + "_test", bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void buildCreativeModeContents(BuildCreativeModeTabContentsEvent event) {
            HolderLookup<Enchantment> enchantmentLookup = event.getParameters().holders().lookupOrThrow(Registries.ENCHANTMENT);
            if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
                ItemStack chestplateStack = new ItemStack(Items.IRON_CHESTPLATE);
                chestplateStack.enchant(enchantmentLookup.getOrThrow(Enchantments.THORNS), 3);
                event.accept(chestplateStack);

                ItemStack swordStack = new ItemStack(Items.DIAMOND_SWORD);
                ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                enchantments.set(enchantmentLookup.getOrThrow(Enchantments.SHARPNESS), 1);
                enchantments.set(enchantmentLookup.getOrThrow(Enchantments.LOOTING), 2);
                EnchantmentHelper.setEnchantments(swordStack, enchantments.toImmutable());
                event.accept(swordStack);
            }
            if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
                event.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantmentLookup.getOrThrow(Enchantments.AQUA_AFFINITY), 1)));
                event.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantmentLookup.getOrThrow(Enchantments.SHARPNESS), 5)));
            }
        }
    }
}
