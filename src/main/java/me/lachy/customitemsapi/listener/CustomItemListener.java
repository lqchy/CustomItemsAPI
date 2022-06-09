package me.lachy.customitemsapi.listener;

import me.lachy.customitemsapi.CustomItemsAPI;
import me.lachy.customitemsapi.items.CustomItemManager;
import me.lachy.customitemsapi.registry.Registry;
import me.lachy.customitemsapi.registry.RegistryType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

@Registry(type = RegistryType.LISTENER)
public class CustomItemListener implements Listener {

    private final CustomItemManager customItemManager;

    public CustomItemListener() {
        this.customItemManager = CustomItemsAPI.get().orElseThrow().getCustomItemManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType().equals(Material.AIR)) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onBlockBreak(event));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType().equals(Material.AIR)) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onBlockPlace(event));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        HumanEntity entity = event.getEntity();
        ItemStack item = entity.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            return;
        }

        this.customItemManager.getCustomItem(item).ifPresent(customItem ->
                customItem.onFoodChange(event));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(item).ifPresent(customItem ->
                customItem.onInteractEvent(event));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onTeleport(event));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onJoin(event));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onQuit(event));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (itemInMainHand.getType() == Material.AIR) {
                return;
            }

            this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                    customItem.onEntityDamageByEntity(event));
        }

        Entity damager = event.getDamager();
        if (damager instanceof Player player) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (itemInMainHand.getType() == Material.AIR) {
                return;
            }

            this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                    customItem.onEntityDamageEntity(event));
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onBucketEmpty(event));
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onEntityInteract(event));
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() == Material.AIR) {
            return;
        }

        this.customItemManager.getCustomItem(item).ifPresent(customItem ->
                customItem.onConsume(event));
    }
}
