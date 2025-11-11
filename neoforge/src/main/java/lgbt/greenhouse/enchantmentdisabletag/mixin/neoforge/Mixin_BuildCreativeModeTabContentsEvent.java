package lgbt.greenhouse.enchantmentdisabletag.mixin.neoforge;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.InsertableLinkedOpenCustomHashSet;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BuildCreativeModeTabContentsEvent.class)
public class Mixin_BuildCreativeModeTabContentsEvent {
    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    @ModifyExpressionValue(method = "assertNewEntryDoesNotAlreadyExists", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/util/InsertableLinkedOpenCustomHashSet;contains(Ljava/lang/Object;)Z"))
    private boolean enchiridion$filterOutInvalidCreativeItemsNeoForge(boolean original, @Local(argsOnly = true) InsertableLinkedOpenCustomHashSet<ItemStack> setToCheck, @Local(argsOnly = true) ItemStack stack) {
        return original &&
                !((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled() &&
                setToCheck.stream().noneMatch(checkStack -> ((Duck_PotentialEnchantmentDisabledStack)(Object)checkStack).enchantmentdisabletag$wasDisabled() && ItemStack.isSameItemSameComponents(checkStack, stack));
    }
}
