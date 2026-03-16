package lgbt.greenhouse.enchantmentdisabletag;

import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentDisableTagFabric implements ModInitializer {
    public static final Identifier REMOVE_OBSOLETE_ITEMS_PHASE = EnchantmentDisableTag.id("remove_obsolete_items");

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("fabric-item-group-api-v1")) {
            CreativeModeTabEvents.MODIFY_OUTPUT_ALL.addPhaseOrdering(Event.DEFAULT_PHASE, REMOVE_OBSOLETE_ITEMS_PHASE);
            CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register(REMOVE_OBSOLETE_ITEMS_PHASE, (group, entries) -> {
                filterOutTabStacks(entries.getDisplayStacks());
                filterOutTabStacks(entries.getSearchTabStacks());
            });
        }
    }

    private static void filterOutTabStacks(List<ItemStack> entries) {
        List<ItemStack> disabledEntries = entries.stream()
                .filter(stack -> ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled())
                .toList();
        List<ItemStack> entriesReference = new ArrayList<>(entries);

        for (ItemStack disabledStack : disabledEntries.reversed()) {
            entriesReference.remove(disabledStack);
            if (((Duck_PotentialEnchantmentDisabledStack)(Object)disabledStack).enchantmentdisabletag$changedToUnenchantedItem() || entriesReference.contains(disabledStack)) { // Check for a duplicate entry.
                entries.remove(disabledStack);
            }
        }
    }
}
