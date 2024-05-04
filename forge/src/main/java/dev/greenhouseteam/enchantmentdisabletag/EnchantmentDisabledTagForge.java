package dev.greenhouseteam.enchantmentdisabletag;

import dev.greenhouseteam.enchantmentdisabletag.platform.EnchantmentDisableTagHelperForge;
import net.minecraftforge.fml.common.Mod;

@Mod(EnchantmentDisableTag.MOD_ID)
public class EnchantmentDisabledTagForge {
    public EnchantmentDisabledTagForge() {
        EnchantmentDisableTag.init(new EnchantmentDisableTagHelperForge());
    }
}