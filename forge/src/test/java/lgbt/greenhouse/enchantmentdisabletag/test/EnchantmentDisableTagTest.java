package lgbt.greenhouse.enchantmentdisabletag.test;

import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod(EnchantmentDisableTag.MOD_ID + "_test")
public class EnchantmentDisableTagTest {
    @Mod.EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID + "_test", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void buildCreativeModeContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
                ItemStack chestplateStack = new ItemStack(Items.IRON_CHESTPLATE);
                chestplateStack.enchant(Enchantments.THORNS, 3);
                event.accept(chestplateStack);

                ItemStack swordStack = new ItemStack(Items.DIAMOND_SWORD);
                EnchantmentHelper.setEnchantments(Map.of(Enchantments.SHARPNESS, 1, Enchantments.MOB_LOOTING, 2), swordStack);
                event.accept(swordStack);
            }
            if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
                ItemStack bookStack = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantedBookItem.addEnchantment(bookStack, new EnchantmentInstance(Enchantments.SHARPNESS, 3));
                EnchantedBookItem.addEnchantment(bookStack, new EnchantmentInstance(Enchantments.THORNS, 2));
                EnchantedBookItem.addEnchantment(bookStack, new EnchantmentInstance(Enchantments.AQUA_AFFINITY, 1));
                event.accept(bookStack);

                event.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.AQUA_AFFINITY, 1)));
                event.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.SHARPNESS, 5)));
            }
        }
    }
}
