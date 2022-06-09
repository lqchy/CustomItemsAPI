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

    private Consumer<BlockBreakEvent> blockBreakEventConsumer;
    private Consumer<BlockPlaceEvent> blockPlaceEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damageByEntityEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damageEntityEventConsumer;
    private Consumer<PlayerJoinEvent> joinEventConsumer;
    private Consumer<PlayerQuitEvent> quitEventConsumer;
    private Consumer<PlayerInteractEvent> interactEventConsumer;
    private Consumer<PlayerInteractAtEntityEvent> interactAtEntityEventConsumer;
    private Consumer<PlayerTeleportEvent> teleportEventConsumer;
    private Consumer<FoodLevelChangeEvent> foodLevelChangeEventConsumer;
    private Consumer<PlayerBucketEmptyEvent> bucketEmptyEventConsumer;
    private Consumer<PlayerItemConsumeEvent> consumeEventConsumer;

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

    public void setBlockBreakEvent(Consumer<BlockBreakEvent> blockBreakEvent) {
        this.blockBreakEventConsumer = blockBreakEvent;
    }

    public void setBlockPlaceEvent(Consumer<BlockPlaceEvent> blockPlaceEvent) {
        this.blockPlaceEventConsumer = blockPlaceEvent;
    }

    public void setDamageByEntityEvent(Consumer<EntityDamageByEntityEvent> damageByEntityEvent) {
        this.damageByEntityEventConsumer = damageByEntityEvent;
    }

    public void setDamageEntityEvent(Consumer<EntityDamageByEntityEvent> damageEntityEvent) {
        this.damageEntityEventConsumer = damageEntityEvent;
    }

    public void setJoinEvent(Consumer<PlayerJoinEvent> joinEvent) {
        this.joinEventConsumer = joinEvent;
    }

    public void setQuitEvent(Consumer<PlayerQuitEvent> quitEvent) {
        this.quitEventConsumer = quitEvent;
    }

    public void setInteractEvent(Consumer<PlayerInteractEvent> interactEvent) {
        this.interactEventConsumer = interactEvent;
    }

    public void setInteractAtEntityEvent(Consumer<PlayerInteractAtEntityEvent> interactAtEntityEvent) {
        this.interactAtEntityEventConsumer = interactAtEntityEvent;
    }

    public void setTeleportEvent(Consumer<PlayerTeleportEvent> teleportEvent) {
        this.teleportEventConsumer = teleportEvent;
    }

    public void setFoodLevelChangeEvent(Consumer<FoodLevelChangeEvent> foodLevelChangeEvent) {
        this.foodLevelChangeEventConsumer = foodLevelChangeEvent;
    }

    public void setBucketEmptyEvent(Consumer<PlayerBucketEmptyEvent> bucketEmptyEvent) {
        this.bucketEmptyEventConsumer = bucketEmptyEvent;
    }

    public void setConsumeEvent(Consumer<PlayerItemConsumeEvent> consumeEvent) {
        this.consumeEventConsumer = consumeEvent;
    }

    public void onBlockBreak(BlockBreakEvent event) {
        Optional.ofNullable(blockBreakEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        Optional.ofNullable(blockPlaceEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Optional.ofNullable(damageByEntityEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        Optional.ofNullable(damageEntityEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onJoin(PlayerJoinEvent event) {
        Optional.ofNullable(joinEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onQuit(PlayerQuitEvent event) {
        Optional.ofNullable(quitEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onTeleport(PlayerTeleportEvent event) {
        Optional.ofNullable(teleportEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onFoodChange(FoodLevelChangeEvent event) {
        Optional.ofNullable(foodLevelChangeEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onInteractEvent(PlayerInteractEvent event) {
        Optional.ofNullable(interactEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Optional.ofNullable(bucketEmptyEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        Optional.ofNullable(interactAtEntityEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onConsume(PlayerItemConsumeEvent event) {
        Optional.ofNullable(consumeEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public String getId() {
        return this.id;
    }

    public void setItemAttributes(Consumer<ItemStack> function) {
        this.itemAttributesConsumer = function;
    }
}
