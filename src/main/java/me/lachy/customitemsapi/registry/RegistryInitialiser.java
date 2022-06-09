package me.lachy.customitemsapi.registry;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import me.lachy.customitemsapi.CustomItemsAPI;
import me.lachy.customitemsapi.items.CustomItemManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RegistryInitialiser {

    private final List<Class<?>> registryClasses;
    private final Plugin plugin;
    private final CustomItemManager customItemManager;

    public RegistryInitialiser(Plugin plugin, CustomItemManager customItemManager) {
        this.plugin = plugin;
        this.customItemManager = customItemManager;

        this.registryClasses = this.getClassesWithAnnotation(Registry.class.getName());
        this.register();
    }

    public void register() {
        RegistryType[] values = RegistryType.values();

        List<RegistryType> collect = Arrays.stream(values)
                .sorted(Comparator.comparing(RegistryType::getPriority).reversed())
                .toList();

        collect.forEach(type -> this.registryClasses.stream()
                .filter(aClass -> aClass.getDeclaredAnnotation(Registry.class).type().equals(type))
                .sorted(Comparator.comparingInt(value -> value.getDeclaredAnnotation(Registry.class).priority().getPriority()))
                .forEach(clazz -> type.register(clazz, this.plugin, this.customItemManager)));
    }

    public List<Class<?>> getClassesWithAnnotation(String annotation) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            String[] pack = this.plugin.getClass().getPackage().getName().split("\\.");
            try (ScanResult result = new ClassGraph().enableAllInfo().acceptPackages(pack[0] + "." + pack[1]).enableClassInfo().scan()) {
                List<Class<?>> clazzes = result.getClassesWithAnnotation(annotation).loadClasses();
                classes.addAll(clazzes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }

}
