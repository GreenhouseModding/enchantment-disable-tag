package lgbt.greenhouse.enchantmentdisabletag.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class EnchantmentDisableTagTest implements ModInitializer {
    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            ItemStack chestplateStack = new ItemStack(Items.IRON_CHESTPLATE);
            chestplateStack.enchant(Enchantments.THORNS, 3);
            entries.accept(chestplateStack);

            ItemStack swordStack = new ItemStack(Items.DIAMOND_SWORD);
            EnchantmentHelper.setEnchantments(Map.of(Enchantments.SHARPNESS, 1, Enchantments.MOB_LOOTING, 2), swordStack);
            entries.accept(swordStack);
        });
    }
}
