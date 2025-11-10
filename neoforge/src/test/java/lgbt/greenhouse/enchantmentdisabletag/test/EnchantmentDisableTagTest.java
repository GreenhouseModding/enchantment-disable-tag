package lgbt.greenhouse.enchantmentdisabletag.test;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.Map;

@Mod(EnchantmentDisableTag.MOD_ID + "_test")
public class EnchantmentDisableTagTest {
    @EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID + "_test", bus = EventBusSubscriber.Bus.MOD)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void buildCreativeModeContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
                HolderLookup<Enchantment> enchantmentLookup = event.getParameters().holders().lookupOrThrow(Registries.ENCHANTMENT);

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
        }
    }
}
