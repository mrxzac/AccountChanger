package org.mrxzac.accountChanger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class MenuClick implements Listener {
    private AccountChanger plugin;
    public MenuClick(AccountChanger plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        // Check if it's the custom GUI
        if (event.getView().getTitle().equals("Choose Your Account")) {
            event.setCancelled(true); // Prevent taking items

            // Check the clicked slot
            if (event.getCurrentItem() != null) {
                String chosen = event.getCurrentItem().getItemMeta().getDisplayName();
                Player player = (Player) event.getWhoClicked();
                plugin.midplayers.put(player.getName(),chosen);
                plugin.getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Changing player "+player.getName()+" to "+chosen+",kick");
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (plugin.midplayers.containsKey(player.getName())) {
                        plugin.midplayers.remove(player.getName());
                        plugin.getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Removed key: " + player.getName() + " after "+plugin.seconds+" seconds.");
                    }
                }, plugin.seconds * 20L);
            }
        }
    }
}
