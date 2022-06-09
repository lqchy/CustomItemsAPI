package me.lachy.customitemsapi;

import me.lachy.customitemsapi.items.CustomItemManagerProvider;
import me.lachy.customitemsapi.registry.RegistryInitialiser;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class CustomItemsAPI extends JavaPlugin {

    private static final AtomicReference<CustomItemsAPI> reference = new AtomicReference<>();
    private CustomItemManagerProvider customItemManager;

    @Override
    public void onEnable() {
        reference.set(this);

        this.customItemManager = new CustomItemManagerProvider();
        new RegistryInitialiser();
    }

    @Override
    public void onDisable() {
        reference.set(null);
    }

    public static Optional<CustomItemsAPI> get() {
        return Optional.ofNullable(reference.get());
    }

    public CustomItemManagerProvider getCustomItemManager() {
        return this.customItemManager;
    }
}
