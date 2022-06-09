package me.lachy.customitemsapi.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CustomItem {
    private final String id;
    private final String displayName;
    private final List<String> lore;
    private final Material material;
    private Consumer<ItemStack> itemAttributesConsumer;

    public abstract void onBlockBreak(BlockBreakEvent event);
    public abstract void onBlockPlace(BlockPlaceEvent event);
    public abstract void onEntityDamageByEntity(EntityDamageByEntityEvent event);
    public abstract void onEntityDamageEntity(EntityDamageByEntityEvent event);
    public abstract void onJoin(PlayerJoinEvent event);
    public abstract void onQuit(PlayerQuitEvent event);
    public abstract void onTeleport(PlayerTeleportEvent event);
    public abstract void onFoodChange(FoodLevelChangeEvent event);
    public abstract void onInteractEvent(PlayerInteractEvent event);
    public abstract void onBucketEmpty(PlayerBucketEmptyEvent event);
    public abstract void onEntityInteract(PlayerInteractAtEntityEvent event);
    public abstract void onConsume(PlayerItemConsumeEvent event);
    public void setItemAttributes(Consumer<ItemStack> function) {
        this.itemAttributesConsumer = function;
    }

    public CustomItem(String id, String displayName, List<String> lore, Material material) {
        this.id = id;
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
    }

    public ItemStack getItem() {
        ItemStack item = ItemStackBuilder.of(this.material)
                .name(this.displayName)
                .lore(this.lore)
                .build();

        Optional.ofNullable(this.itemAttributesConsumer)
                .ifPresent(function -> function.accept(item));

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("IsCustom", true);
        nbtItem.setString("CustomItemType", this.id);
        nbtItem.applyNBT(item);

        return nbtItem.getItem();
    }

    public abstract Recipe getRecipe();

    public String getId() {
        return this.id;
    }
}
