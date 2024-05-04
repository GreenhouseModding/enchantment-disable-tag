package dev.greenhouseteam.enchantmentdisabletag;

import dev.greenhouseteam.enchantmentdisabletag.platform.EnchantmentDisableTagHelperForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(EnchantmentDisableTag.MOD_ID)
public class EnchantmentDisabledTagForge {
    public EnchantmentDisabledTagForge() {
        EnchantmentDisableTag.init(new EnchantmentDisableTagHelperForge());
    }

    @Mod.EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onUpdateTags(TagsUpdatedEvent event) {
            if (event.getUpdateCause().equals(TagsUpdatedEvent.UpdateCause.CLIENT_PACKET_RECEIVED))
                EnchantmentDisableTag.setReloaded();
        }
    }
}