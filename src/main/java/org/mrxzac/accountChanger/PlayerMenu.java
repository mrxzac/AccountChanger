package org.mrxzac.accountChanger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerMenu {

    public static void openMenu(Player player, List<?> playerslist,int maxplayers) {
        // Create a 3-row inventory (size must be a multiple of 9)
        Inventory gui = Bukkit.createInventory(null, ((int)(maxplayers/9)+1)*9, "Choose Your Account");
        for (int i=0;i< playerslist.size();i++) {
            //set each player head to the inventory and name as the player name.
            gui.setItem(i,PlayerHead.getPlayerHead((String) playerslist.get(i)));
        }

        // Open the GUI for the player
        player.openInventory(gui);
    }
}
