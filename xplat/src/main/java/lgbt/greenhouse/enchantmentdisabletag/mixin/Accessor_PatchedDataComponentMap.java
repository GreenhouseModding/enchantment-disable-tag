package lgbt.greenhouse.enchantmentdisabletag.mixin;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PatchedDataComponentMap.class)
public interface Accessor_PatchedDataComponentMap {
    @Accessor("prototype")
    DataComponentMap enchantmentdisabletag$getPrototype();
}
