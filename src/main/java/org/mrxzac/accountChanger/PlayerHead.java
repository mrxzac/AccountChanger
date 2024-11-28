package org.mrxzac.accountChanger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHead{

    public static ItemStack getPlayerHead(String playerName) {
        // Create a new ItemStack for the player head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);

        // Get the meta for the player head
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        // Set the owner of the head to the player's name
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        skullMeta.setOwningPlayer(player);

        // Optionally, set a display name for the item
        skullMeta.setDisplayName(playerName);

        // Apply the meta to the item
        head.setItemMeta(skullMeta);

        return head;
    }
}
