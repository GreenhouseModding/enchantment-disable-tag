package lgbt.greenhouse.enchantmentdisabletag;

import lgbt.greenhouse.enchantmentdisabletag.duck.Duck_PotentialEnchantmentDisabledStack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentDisableTagFabric implements ModInitializer {
    public static final ResourceLocation REMOVE_OBSOLETE_ITEMS_PHASE = EnchantmentDisableTag.id("remove_obsolete_items");

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("fabric-item-group-api-v1")) {
            ItemGroupEvents.MODIFY_ENTRIES_ALL.addPhaseOrdering(Event.DEFAULT_PHASE, REMOVE_OBSOLETE_ITEMS_PHASE);
            ItemGroupEvents.MODIFY_ENTRIES_ALL.register(REMOVE_OBSOLETE_ITEMS_PHASE, (group, entries) -> {
                filterOutTabStacks(entries.getDisplayStacks());
                filterOutTabStacks(entries.getSearchTabStacks());
            });
        }
    }

    @SuppressWarnings({"DataFlowIssue", "ConstantValue"})
    private static void filterOutTabStacks(List<ItemStack> entries) {
        List<ItemStack> disabledEntries = entries.stream()
                .filter(stack -> ((Duck_PotentialEnchantmentDisabledStack)(Object)stack).enchantmentdisabletag$wasDisabled())
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(disabledEntries);
        List<ItemStack> entriesReference = new ArrayList<>(entries);

        for (ItemStack disabledStack : disabledEntries) {
            entriesReference.remove(disabledStack);
            if (
                    disabledStack.is(Items.ENCHANTED_BOOK) && EnchantedBookItem.getEnchantments(disabledStack).isEmpty() ||
                    ((Duck_PotentialEnchantmentDisabledStack)(Object)disabledStack).enchantmentdisabletag$changedToUnenchantedItem() ||
                    entriesReference.contains(disabledStack) // Check for a duplicate entry.
            ) {
                entries.remove(disabledStack);
            }
        }
    }
}
