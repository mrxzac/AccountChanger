package org.mrxzac.accountChanger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

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
                String chosen = plugin.playerItemsinv.get(event.getCurrentItem().getType());//the chosen name
                Player player = (Player) event.getWhoClicked();//the player clicks

                var key = plugin.originals.get(player.getName());//player's original name
                var nameori = (key!=null)?(key):(player.getName());//double check

                plugin.getLogger().info(player.getName() + "'s original is" + nameori);

                plugin.originals.put(player.getName(), nameori);//last name player used to use. <this name,original name>

                plugin.chosen.put(player.getName(), Boolean.TRUE);//this player chose a new name

                if(!nameori.equals(chosen)) {
                    //player didn't change to original

                    plugin.midplayers.put(nameori, chosen);//player's alias stored

                    plugin.getLogger().info("Player " + player.getName() + "changed to " + chosen + ",kick");

                    player.closeInventory();// goes to menu quit.
                }else {
                    //player changed to original
                    plugin.midplayers.remove(nameori);

                    plugin.getLogger().info("Player " + player.getName() + "changed to original ,kick");

                    player.closeInventory();

                }
            }
        }
    }
}
