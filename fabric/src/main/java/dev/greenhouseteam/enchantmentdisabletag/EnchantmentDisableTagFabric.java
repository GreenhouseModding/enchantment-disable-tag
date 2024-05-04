package dev.greenhouseteam.enchantmentdisabletag;

import dev.greenhouseteam.enchantmentdisabletag.platform.EnchantmentDisableTagHelperFabric;
import net.fabricmc.api.ModInitializer;

public class EnchantmentDisableTagFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        EnchantmentDisableTag.init(new EnchantmentDisableTagHelperFabric());
    }
}