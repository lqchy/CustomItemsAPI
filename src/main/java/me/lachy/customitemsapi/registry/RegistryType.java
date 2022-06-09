package me.lachy.customitemsapi.registry;

import lombok.SneakyThrows;
import me.lachy.customitemsapi.items.CustomItem;
import me.lachy.customitemsapi.items.CustomItemManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

public enum RegistryType {

    LISTENER("Listener", 3),
    ITEM("Custom Item", 2);

    private final String name;
    private final int priority;

    RegistryType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @SneakyThrows
    public void register(Class<?> clazz, Plugin plugin, CustomItemManager customItemManager) {
        switch (this) {
            case LISTENER -> Bukkit.getPluginManager()
                    .registerEvents((Listener) clazz.getDeclaredConstructor().newInstance(), plugin);
            case ITEM -> {
                CustomItem customItem = (CustomItem) clazz.getDeclaredConstructor().newInstance();
                customItemManager.registerCustomItem(customItem);

                Recipe recipe = customItem.getRecipe();
                if (recipe != null) {
                    Bukkit.getServer().addRecipe(recipe);
                }
            }
            default -> clazz.getDeclaredConstructor().newInstance();
        }
        this.send(clazz, plugin);
    }

    private void send(Class<?> clazz, Plugin plugin) {
        String className = (getName().isEmpty() ? "" : " " + getName());
        plugin.getLogger().info("Registered" + className + " " + clazz.getSimpleName() + "");
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }
}
