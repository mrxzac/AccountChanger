package org.mrxzac.accountChanger;

import com.comphenix.protocol.PacketType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;

public class JoiningEvent implements Listener{
    private AccountChanger plugin;
    public JoiningEvent(AccountChanger plugin){
        this.plugin=plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Player "+event.getPlayer().getName()+plugin.midplayers.get(event.getPlayer().getName())+" joining");
        if(!plugin.players.contains(event.getPlayer().getName())) {
            plugin.getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Opening GUI");
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                event.getPlayer().sendMessage("Choose An Account To Login");
                PlayerMenu.openMenu(event.getPlayer(),plugin.players,plugin.maxplayer);

            }, 10L);
        }
    }
}
