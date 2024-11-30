package org.mrxzac.accountChanger;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class Menuquit implements Listener {
    private final AccountChanger plugin;
    public Menuquit(AccountChanger plugin){
        this.plugin=plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // Check if the player is the one who closed the inventory
        if (event.getView().getTitle().equals("Choose Your Account")) {
            if (event.getPlayer() instanceof Player player) {
                // Kick the player with a custom message

                var key = plugin.originals.get(player.getName());//player's original name

                var nameori = (key!=null)?(key):(player.getName());//player has no original name, then original name is player's name

                if(!plugin.chosen.get(player.getName())) {//if he does not choose a new name
                    plugin.getLogger().info("No Item Chosen");
                    if(plugin.players.contains(player.getName())){
                        //this player has already chosen an alias, but opened menu again
                        //do nothing
                        plugin.getLogger().info(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + player.getName() + "opened menu but no changed name");
                    }else {
                        //this player hasn't chosen an alias, but closed the menu, kick
                        player.kickPlayer("Rejoin and choose the Account to join");
                        //as same as this player never existed before
                        plugin.midplayers.put(player.getName(),player.getName());
                        plugin.midplayers.remove(player.getName());

                        plugin.originals.put(player.getName(),player.getName());
                        plugin.originals.remove(player.getName());

                        plugin.getLogger().info(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + player.getName() + " was kicked for closing the Choosing menu.");
                    }
                }else{
                    plugin.getLogger().info("Item Chosen");
                    //this player has chosen an alias, no matter he is in list or not, kick
                    var shown =plugin.midplayers.get(plugin.originals.get(player.getName()));//get his new name
                    player.kickPlayer("Changing Account to "+ChatColor.AQUA+((shown!=null)?shown:"original account")+ChatColor.GOLD+"\nPlease Rejoin in");
                    //plugin.getLogger().info(plugin.midplayers.toString());
                    plugin.originals.remove(player.getName());
                }
            }
        }
    }
}
