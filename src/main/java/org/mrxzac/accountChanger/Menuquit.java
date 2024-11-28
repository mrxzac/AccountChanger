package org.mrxzac.accountChanger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mrxzac.accountChanger.AccountChanger;
import org.mrxzac.accountChanger.MenuClick;

public class Menuquit implements Listener {
    private AccountChanger plugin;
    public Menuquit(AccountChanger plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Check if the player is the one who closed the inventory
        if (event.getView().getTitle().equals("Choose Your Account")) {
            if (event.getPlayer() instanceof Player player) {
                // Kick the player with a custom message
                if(plugin.midplayers.get(player.getName())==player.getName()) {
                    player.kickPlayer("Choose the Account to join");
                    plugin.midplayers.remove(player.getName());
                    plugin.getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+player.getName() + " was kicked for closing the Choosing menu.");
                }else{
                    player.kickPlayer("Changing Account to "+ChatColor.AQUA+plugin.midplayers.get(player.getName())+ChatColor.RESET+
                            ".\nPlease Rejoin in "+ChatColor.GOLD+"["+plugin.seconds+"]"+ChatColor.RESET+
                            " seconds,\n before "+ChatColor.GOLD+(LocalDateTime.now().plusSeconds(plugin.seconds).format(DateTimeFormatter.ofPattern("HH:mm:ss")))+ ChatColor.RED +"\nor will return to original");
                }
            }
        }
    }
}
