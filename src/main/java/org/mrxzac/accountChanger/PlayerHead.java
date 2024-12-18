package org.mrxzac.accountChanger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class PlayerHead{

    public static ItemStack getPlayerHead(String playerName, HashMap<String,Material> playerItem) {
        // Create a new ItemStack for the player head
        Material material =playerItem.get(playerName);
        if(material==null)material=Material.BARRIER;
        ItemStack item = new ItemStack(material,1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            // Set a custom name for the item
            meta.setDisplayName(ChatColor.GOLD+" "+ChatColor.ITALIC+(playerName)+" ");

            // Apply the ItemMeta back to the ItemStack
            item.setItemMeta(meta);
        }

        return item;
    }
}
