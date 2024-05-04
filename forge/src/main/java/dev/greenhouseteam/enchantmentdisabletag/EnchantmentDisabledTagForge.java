package dev.greenhouseteam.enchantmentdisabletag;

import dev.greenhouseteam.enchantmentdisabletag.platform.EnchantmentDisableTagHelperForge;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod(EnchantmentDisableTag.MOD_ID)
public class EnchantmentDisabledTagForge {
    public EnchantmentDisabledTagForge() {
        EnchantmentDisableTag.init(new EnchantmentDisableTagHelperForge());
    }

    @Mod.EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void removeFromCreativeModeTab(BuildCreativeModeTabContentsEvent event) {
            for (Map.Entry<ItemStack, CreativeModeTab.TabVisibility> entry : event.getEntries())
                if (entry.getKey().getItem() instanceof EnchantedBookItem && EnchantedBookItem.getEnchantments(entry.getKey()).isEmpty())
                    event.getEntries().remove(entry.getKey());
        }
    }
}