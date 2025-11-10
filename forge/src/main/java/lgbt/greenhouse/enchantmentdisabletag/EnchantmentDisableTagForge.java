package lgbt.greenhouse.enchantmentdisabletag;

import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(EnchantmentDisableTag.MOD_ID)
public class EnchantmentDisableTagForge {
    public EnchantmentDisableTagForge() {
    }

    @Mod.EventBusSubscriber(modid = EnchantmentDisableTag.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onUpdateTags(TagsUpdatedEvent event) {
            EnchantmentDisableTag.setCreativeTabToReload();
        }
    }
}