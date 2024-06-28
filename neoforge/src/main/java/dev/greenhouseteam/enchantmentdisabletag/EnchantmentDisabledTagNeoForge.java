package dev.greenhouseteam.enchantmentdisabletag;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

public class EnchantmentDisabledTagNeoForge {
    @EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents {
        @SubscribeEvent
        public static void onUpdateTags(TagsUpdatedEvent event) {
            EnchantmentDisableTag.setCreativeTabToReload();
        }
    }

}