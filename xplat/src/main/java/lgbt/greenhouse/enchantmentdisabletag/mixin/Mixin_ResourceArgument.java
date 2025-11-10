package lgbt.greenhouse.enchantmentdisabletag.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import lgbt.greenhouse.enchantmentdisabletag.EnchantmentDisableTag;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ResourceArgument.class)
public class Mixin_ResourceArgument<T> {

    @SuppressWarnings("unchecked")
    @ModifyReturnValue(method = "parse(Lcom/mojang/brigadier/StringReader;)Lnet/minecraft/core/Holder$Reference;", at = @At(value = "RETURN"))
    private Holder.Reference<T> enchantmentdisabletag$throwOnDisabled(Holder.Reference<T> original) throws CommandSyntaxException {
        if (original.key().isFor(Registries.ENCHANTMENT) && original.is((TagKey<T>) EnchantmentDisableTag.DISABLED)) {
            throw new DynamicCommandExceptionType(
                    key -> Component.translatableWithFallback("command.enchantmentdisabledtag.disabled", "Enchantment " + key + " has been disabled via the enchantmentdisabletag:disabled enchantment tag.", key)
            ).create(original.key().location().toString());
        }
        return original;
    }
}
