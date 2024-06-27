package dev.greenhouseteam.enchantmentdisabletag.mixin;

import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTag;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.greenhouseteam.enchantmentdisabletag.EnchantmentDisableTags;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.stream.Stream;

@Mixin(ResourceArgument.class)
public class ResourceArgumentMixin<T> {
    @Shadow @Final
    ResourceKey<? extends Registry<T>> registryKey;

    @ModifyReturnValue(method = "parse(Lcom/mojang/brigadier/StringReader;)Lnet/minecraft/core/Holder$Reference;", at = @At(value = "RETURN"))
    private Holder.Reference<T> enchantmentdisabletag$throwOnDisabled(Holder.Reference<T> original) throws CommandSyntaxException {
        if (original.key().isFor(Registries.ENCHANTMENT) && original.is((TagKey<T>) EnchantmentDisableTags.DISABLED))
            throw new DynamicCommandExceptionType(
                    key -> Component.translatableWithFallback("command.enchantmentdisabledtag.disabled", "Enchantment " + key + " has been disabled via the enchantmentdisabletag:disabled enchantment tag.", key)
            ).create(original.key().location().toString());
        return original;
    }

    @ModifyArg(method = "listSuggestions", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/SharedSuggestionProvider;suggestResource(Ljava/util/stream/Stream;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;"), index = 0)
    private Stream<ResourceLocation> enchantmentdisabletag$listNonDisabledSuggestions(Stream<ResourceLocation> original) {
        if (!registryKey.location().equals(Registries.ENCHANTMENT.location()))
            return original;
        return original.filter(id -> EnchantmentDisableTag.getHelper().getHolder(id).map(holder -> !holder.is(EnchantmentDisableTags.DISABLED)).orElse(true));
    }
}
