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

            if(plugin.midplayers.containsKey(originalUsername)){

                String newUsername = plugin.midplayers.get(originalUsername);

                plugin.originals.put(newUsername,originalUsername);

                event.getPacket().getStrings().write(0, newUsername);

                getPlugin().getLogger().info("Intercepted login packet. Changed username from "
                        + originalUsername + " to " + newUsername);

            }else {
                if(plugin.players.contains(originalUsername)){
                    getPlugin().getLogger().info("Player "+originalUsername+" is in list and not change alias, direct join");
                }else {
                    getPlugin().getLogger().info("Player "+originalUsername+" is not list and not change alias, direct join");
                    //wait joined to force change player's alias
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
