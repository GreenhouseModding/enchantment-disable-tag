package lgbt.greenhouse.enchantmentdisabletag;

import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnchantmentDisableTagFabric implements ModInitializer {
    public static final ResourceLocation REMOVE_OBSOLETE_ITEMS_PHASE = EnchantmentDisableTag.id("remove_obsolete_items");

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void onInitialize() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(Event.DEFAULT_PHASE, REMOVE_OBSOLETE_ITEMS_PHASE);
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(REMOVE_OBSOLETE_ITEMS_PHASE, (group, entries) -> {
            filterOutTabStacks(entries.getDisplayStacks());
            filterOutTabStacks(entries.getSearchTabStacks());
        });
    }

    @SuppressWarnings("DataFlowIssue")
    private static void filterOutTabStacks(List<ItemStack> entries) {
        List<ItemStack> processedTabEntries = new ArrayList<>();

        for (Iterator<ItemStack> it = entries.iterator(); it.hasNext();) {
            ItemStack stack = it.next();
            if (
                    ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled() && processedTabEntries.stream().anyMatch(existingStack -> ItemStack.isSameItemSameComponents(stack, existingStack))
            ) {
                it.remove();
            }
            processedTabEntries.add(stack);
        }
    }
}
