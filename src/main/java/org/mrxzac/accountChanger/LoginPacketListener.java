package org.mrxzac.accountChanger;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class LoginPacketListener extends PacketAdapter {
    private final AccountChanger plugin;
    public LoginPacketListener(AccountChanger plugin) {
        super(  plugin,
                ListenerPriority.NORMAL,
                PacketType.Login.Client.START
        );
        this.plugin=plugin;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        // Intercept the login start packet
        try {
            // Get the original username
            var originalUsername = event.getPacket().getStrings().read(0);

            if(plugin.players.contains(originalUsername)){
                // player who do not require account choosing
                if(plugin.midplayers.containsKey(originalUsername)){
                    String newUsername = plugin.midplayers.get(originalUsername);
                    //plugin.midplayers.remove(originalUsername);
                    getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Players in Mid stages:"+plugin.midplayers.toString());
                    event.getPacket().getStrings().write(0, newUsername);

                    getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Intercepted login packet. Changed username from "
                            + originalUsername + " to " + newUsername);
                }else {
                    plugin.midplayers.put(originalUsername,originalUsername);
                    getPlugin().getLogger().info(ChatColor.AQUA + "[AccountChanger]" + ChatColor.RESET + "Player " + originalUsername + " is in players list,joining");
                }
            }else {
                getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Player "+originalUsername+" is not in player list");
                boolean Joined = plugin.midplayers.containsKey(originalUsername);

                if(!Joined) {
                    getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Player "+originalUsername+" choosing username");
                    //player who require account choosing, align the username to tobechoosing.
                    plugin.midplayers.put(originalUsername,originalUsername);

                }else if(Joined){
                    getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Player "+originalUsername+" changing to new name");
                    // player who rejoined from account choosing
                    // Modify the username to an alternate account
                    String newUsername = plugin.midplayers.get(originalUsername);
                    //plugin.midplayers.remove(originalUsername);
                    getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Players in Mid stages:"+plugin.midplayers.toString());
                    event.getPacket().getStrings().write(0, newUsername);

                    getPlugin().getLogger().info(ChatColor.AQUA+"[AccountChanger]"+ChatColor.RESET+"Intercepted login packet. Changed username from "
                            + originalUsername + " to " + newUsername);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
