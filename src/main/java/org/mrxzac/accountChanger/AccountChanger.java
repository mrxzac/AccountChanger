package org.mrxzac.accountChanger;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Supplier;

public final class AccountChanger extends JavaPlugin {
    private ProtocolManager protocolManager;
    public List<?> players;
    public int maxplayer;
    public int seconds;
    public HashMap<String, String> midplayers = new HashMap<String, String>();
    public HashMap<String, String> lastori = new HashMap<String, String>();

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
        this.getCommand("accountchanger").setTabCompleter(new Tabcomplete());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static <K, V> K getKeyByValue(HashMap<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null; // Return null if no matching key is found
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("accountchanger")) {
            if(args.length==0){
                sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                return false;
            }
            if(args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("accountchanger.reload")) {
                    reloadConfig(); // Reload the config from disk
                    maxplayer = getConfig().getInt("MaxPlayers");
                    players = getConfig().getList("Players");
                    seconds = getConfig().getInt("MaxWait");
                    sender.sendMessage(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + "Configuration reloaded successfully!");
                    sender.sendMessage(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + "Official Players:" + players.toString());
                    sender.sendMessage(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + "MaxPlayer:" + maxplayer + "|MaxWait:" + seconds);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return true;
                }
            }else if(args[0].equalsIgnoreCase("switch")){
                if (sender.hasPermission("accountchanger.switch")) {
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
                        return true;
                    }

                    //midplayers.put(player.getName(), player.getName());
                    PlayerMenu.openMenu(player, players, maxplayer);
                }

            }else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.GREEN + "=== Account Changer Help ===");
                sender.sendMessage(ChatColor.YELLOW + "/ac reload - reload config");
                sender.sendMessage(ChatColor.YELLOW + "/ac switch - switch account");
                sender.sendMessage(ChatColor.YELLOW + "/ac clear <player> - clear state for account");
                sender.sendMessage(ChatColor.YELLOW + "/ac help - Show this help message");
                return true;
            }else if(args[0].equalsIgnoreCase("clear")){
                if(args.length==1) {
                    if(sender.hasPermission("accountchanger.clear")) {
                        midplayers.remove(getKeyByValue(midplayers, sender.getName()));
                        Bukkit.getPlayerExact(sender.getName()).kickPlayer("Status Reset, Please Rejoin");
                        sender.sendMessage(ChatColor.AQUA + "Cleared state for " + sender.getName());
                        return true;
                    }else {
                        sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                        return false;
                    }
                }else if(args.length==2) {
                    if(sender.hasPermission("accountchanger.clear.all")){
                        midplayers.remove(getKeyByValue(midplayers, args[1]));
                        Player kicking = Bukkit.getPlayerExact(args[1]);
                        if(kicking!=null) {
                            kicking.kickPlayer("Status Reset, Please Rejoin");
                            sender.sendMessage(ChatColor.AQUA + "Cleared state for " + args[1]);
                            return true;
                        }else {
                            sender.sendMessage(ChatColor.RED + "Player Not found");
                            return false;
                        }
                    }else{
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return false;
                    }
                }else {
                    sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                    return false;

                }
            }else {
                sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                return false;
            }
        }
        return false;
    }
}
