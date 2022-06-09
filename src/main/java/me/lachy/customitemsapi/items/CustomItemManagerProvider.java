package me.lachy.customitemsapi.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CustomItemManagerProvider implements CustomItemManager {
    private final ArrayList<CustomItem> cache = new ArrayList<>();

    @Override
    public void registerCustomItem(@NotNull CustomItem customItem) {
        this.cache.add(customItem);
    }

    @Override
    public Optional<CustomItem> getCustomItem(@NotNull String name) {
        return this.cache.stream()
                .filter(customItem -> customItem.getId().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Optional<CustomItem> getCustomItem(@NotNull ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("IsCustom") || !nbtItem.hasKey("CustomItemType")) {
            return Optional.empty();
        }

        Collection<CustomItem> customItems = this.getCustomItems();
        return customItems.stream()
                .filter(customItem -> customItem.getId().equalsIgnoreCase(nbtItem.getString("CustomItemType")))
                .findFirst();
    }

    @Override
    public Collection<CustomItem> getCustomItems() {
        return this.cache;
    }
}
