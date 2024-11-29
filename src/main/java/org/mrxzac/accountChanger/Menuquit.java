package org.mrxzac.accountChanger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mrxzac.accountChanger.AccountChanger;
import org.mrxzac.accountChanger.MenuClick;

public class Menuquit implements Listener {
    private final AccountChanger plugin;
    public Menuquit(AccountChanger plugin){
        this.plugin=plugin;
    }

    public static <K, V> K getKeyByValue(HashMap<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null; // Return null if no matching key is found
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Check if the player is the one who closed the inventory
        if (event.getView().getTitle().equals("Choose Your Account")) {
            if (event.getPlayer() instanceof Player player) {
                // Kick the player with a custom message

                var key = getKeyByValue(plugin.midplayers,player.getName());
                var nameori = (key!=null)?(key):player.getName();
                if(Objects.equals(plugin.midplayers.get(nameori), player.getName())) {
                    if(plugin.players.contains(player.getName())){
                        //plugin.midplayers.remove(player.getName());
                        plugin.midplayers.put(nameori,player.getName());
                        plugin.getLogger().info(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + player.getName() + " wants to change name");
                    }else {
                        player.kickPlayer("Rejoin and choose the Account to join");
                        //plugin.midplayers.remove(player.getName());
                        plugin.midplayers.put(nameori,player.getName());
                        plugin.getLogger().info(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + player.getName() + " was kicked for closing the Choosing menu.");
                    }
                }else{
//                    player.kickPlayer("Changing Account to "+ChatColor.AQUA+plugin.midplayers.get(player.getName())+ChatColor.RESET+
//                            ".\nPlease Rejoin in "+ChatColor.GOLD+"["+plugin.seconds+"]"+ChatColor.RESET+
//                            " seconds,\n before "+ChatColor.GOLD+(LocalDateTime.now().plusSeconds(plugin.seconds).format(DateTimeFormatter.ofPattern("HH:mm:ss")))+ ChatColor.RED +"\nor will return to original");
                    plugin.getLogger().info(nameori);
                    var shown =plugin.midplayers.get(plugin.lastori.get(nameori));
                    player.kickPlayer("Changing Account to "+ChatColor.AQUA+((shown!=null)?shown:"original account")+ChatColor.GOLD+"\nPlease Rejoin in");
                    plugin.getLogger().info(plugin.midplayers.toString());
                }
            }
        }
    }
}
