package dev.greenhouseteam.enchantmentdisabletag;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@Mod(EnchantmentDisableTag.MOD_ID)
public class EnchantmentDisabledTagNeoForge {
    public EnchantmentDisabledTagNeoForge(IEventBus eventBus) {
    }

    @EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents {
        @SubscribeEvent
        public static void onUpdateTags(TagsUpdatedEvent event) {
            EnchantmentDisableTag.setReloaded();
        }
    }

}