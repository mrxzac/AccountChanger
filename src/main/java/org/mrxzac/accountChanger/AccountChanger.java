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

public final class AccountChanger extends JavaPlugin {
    private ProtocolManager protocolManager;
    public List<?> players;
    public int maxplayer;
    public int ticks;
    public HashMap<String, String> midplayers = new HashMap<String, String>();//original name, alias name
    public HashMap<String, String> originals = new HashMap<String, String>();//alias name, original name
    public HashMap<String, Boolean> chosen = new HashMap<String, Boolean>();

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
        ticks = getConfig().getInt("MaxWait");
        getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Official Players:"+players.toString());

        //Add listeners
        protocolManager.addPacketListener(new LoginPacketListener(this));//Names Listener
        getServer().getPluginManager().registerEvents(new JoiningEvent(this),this);//GUI Open Listener
        getServer().getPluginManager().registerEvents(new MenuClick(this), this);//GUI Select Listener
        getServer().getPluginManager().registerEvents(new Menuquit(this), this);//GUI quit unexpected
        this.getCommand("accountchanger").setTabCompleter(new Tabcomplete());//Tabcompleter
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("accountchanger")) {
            if(args.length==0){
                sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                return false;
            }
            if(args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("accountchanger.reload")) {// reload command
                    reloadConfig(); // Reload the config from disk
                    maxplayer = getConfig().getInt("MaxPlayers");
                    players = getConfig().getList("Players");
                    ticks = getConfig().getInt("MaxWait");
                    midplayers = new HashMap<String, String>();
                    originals = new HashMap<String, String>();
                    chosen = new HashMap<String, Boolean>();
                    sender.sendMessage("Configuration reloaded successfully!");
                    sender.sendMessage("Official Players:" +ChatColor.GOLD+ players.toString());
                    sender.sendMessage("MaxPlayer:" +ChatColor.GOLD+ maxplayer );
                    sender.sendMessage("MaxWait:" +ChatColor.GOLD+ ticks);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }

            }else if(args[0].equalsIgnoreCase("switch")){ // switch command
                if (sender.hasPermission("accountchanger.switch")) {
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
                        return false;
                    }

                    chosen.put(String.valueOf(sender),Boolean.FALSE);
                    PlayerMenu.openMenu(player, players, maxplayer);
                    return true;
                }

            }else if (args[0].equalsIgnoreCase("help")) {// help command
                sender.sendMessage(ChatColor.GREEN + "=== Account Changer Help ===");
                sender.sendMessage(ChatColor.YELLOW + "/ac reload - reload config");
                sender.sendMessage(ChatColor.YELLOW + "/ac switch - switch account");
                sender.sendMessage(ChatColor.YELLOW + "/ac list - check listed accounts");
                sender.sendMessage(ChatColor.YELLOW + "/ac original - last alias => original");
                sender.sendMessage(ChatColor.YELLOW + "/ac alias - original => alias");
                sender.sendMessage(ChatColor.YELLOW + "/ac clear <player> - clear state for account");
                sender.sendMessage(ChatColor.YELLOW + "/ac help - Show this help message");
                return true;

            }else if(args[0].equalsIgnoreCase("clear")) {//clear command
                if (args.length == 1) {
                    if (sender.hasPermission("accountchanger.clear") && !sender.getName().equals("Server")) {
                        midplayers.remove(originals.get(sender.getName()));
                        originals.remove(sender.getName());
                        Bukkit.getPlayerExact(sender.getName()).kickPlayer("Status Reset, Please Rejoin");
                        sender.sendMessage(ChatColor.AQUA + "Cleared state for " + sender.getName());
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                        return false;
                    }
                } else if (args.length == 2) {
                    if (sender.hasPermission("accountchanger.clear.all")) {
                        midplayers.remove(originals.get(args[1]));
                        originals.remove(args[1]);

                        Player kicking = Bukkit.getPlayerExact(args[1]);
                        if (kicking != null) {
                            kicking.kickPlayer("Status Reset, Please Rejoin");
                        }
                        sender.sendMessage(ChatColor.AQUA + "Cleared state for " + args[1]);
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return false;//no permission for clear.all
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                    return false;//wrong command in clear

                }
            }else if(args[0].equalsIgnoreCase("alias")) {
                sender.sendMessage("Original => Alias");
                if (sender.hasPermission("accountchanger.alias")) {
                    for (Map.Entry<String, String> player : midplayers.entrySet()) {
                        sender.sendMessage(ChatColor.AQUA+player.getKey() + ChatColor.RESET+
                                "=alias as=>"+ChatColor.GOLD+ player.getValue());
                    }
                    return true;
                }else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }
            }else if(args[0].equalsIgnoreCase("original")){
                if (sender.hasPermission("accountchanger.original")) {
                    sender.sendMessage("Last alias => Original ");
                    for (Map.Entry<String, String> player : originals.entrySet()) {
                        sender.sendMessage(ChatColor.GOLD+player.getKey() +ChatColor.RESET+
                                "=original is=>" +ChatColor.AQUA+ player.getValue());
                    }
                    return true;
                }else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }

            }else if(args[0].equalsIgnoreCase("list")){
                if (sender.hasPermission("accountchanger.list")) {
                    sender.sendMessage("Listed Players:");
                    for (int i=0;i<players.size();i++) {
                        sender.sendMessage(ChatColor.GOLD + players.get(i).toString());
                    }
                    return true;
                }else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return false;
                }

            }else {// other non listed command
                sender.sendMessage(ChatColor.RED + "Unknown command. /ac help for help");
                return false;// wrong command
            }
        }
        return false;
    }
}
