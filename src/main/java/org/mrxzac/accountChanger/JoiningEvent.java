package org.mrxzac.accountChanger;

import com.comphenix.protocol.PacketType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class JoiningEvent implements Listener{
    private AccountChanger plugin;
    public JoiningEvent(AccountChanger plugin){
        this.plugin=plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        var key = plugin.originals.get(player.getName());//player's original name
        var nameori = (key!=null)?(key):("[No alias]");//double check

        plugin.getLogger().info("Player "+player.getName()+" with alias "+nameori+" joining");

        if(!plugin.players.contains(player.getName())) {//player not in list
            //open gui for the player
            plugin.getLogger().info("Opening GUI for "+player.getName());
            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                event.getPlayer().sendMessage("Choose An Account To Login");
                plugin.chosen.put(event.getPlayer().getName(),Boolean.FALSE);// mark player start choosing
                PlayerMenu.openMenu(event.getPlayer(),plugin.players,plugin.maxplayer);

            }, (long) plugin.ticks);
        }
    }
}
