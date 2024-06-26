package dev.greenhouseteam.enchantmentdisabletag;

import dev.greenhouseteam.enchantmentdisabletag.platform.EnchantmentDisableTagHelperFabric;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class EnchantmentDisableTagFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        EnchantmentDisableTag.init(new EnchantmentDisableTagHelperFabric());
    }
}