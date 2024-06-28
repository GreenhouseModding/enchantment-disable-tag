package dev.greenhouseteam.enchantmentdisabletag.mixin;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MappedRegistry.class)
public interface MappedRegistryAccessor<T> {
    @Accessor("byId")
    ObjectList<Holder.Reference<T>> enchantmentdisabletag$getById();
}
