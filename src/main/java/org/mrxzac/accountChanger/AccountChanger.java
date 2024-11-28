package org.mrxzac.accountChanger;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public final class AccountChanger extends JavaPlugin {
    private ProtocolManager protocolManager;
    public List<?> players;
    public int maxplayer;
    public int seconds;
    public HashMap<String, String> midplayers = new HashMap<String, String>();

    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            getLogger().severe(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"ProtocolLib not found! Disabling AccountSwitcher plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        maxplayer = getConfig().getInt("MaxPlayers");
        players = getConfig().getList("Players");
        seconds = getConfig().getInt("MaxWait");
        getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Official Players:"+players.toString());

        //Add listeners
        protocolManager.addPacketListener(new LoginPacketListener(this));//Names Listener
        getServer().getPluginManager().registerEvents(new JoiningEvent(this),this);//GUI Open Listener
        getServer().getPluginManager().registerEvents(new MenuClick(this), this);//GUI Select Listener
        getServer().getPluginManager().registerEvents(new Menuquit(this), this);//GUI quit unexpected
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("acreload")) {
            if (sender.hasPermission("accountchanger.reload")) {
                reloadConfig(); // Reload the config from disk
                maxplayer = getConfig().getInt("MaxPlayers");
                players = getConfig().getList("Players");
                seconds = getConfig().getInt("MaxWait");
                sender.sendMessage(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Configuration reloaded successfully!");
                sender.sendMessage(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Official Players:"+players.toString());
                sender.sendMessage(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"MaxPlayer:"+maxplayer+"|MaxWait:"+seconds);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED+"You do not have permission to use this command.");
                return true;
            }
        }
        return false;
    }
}
