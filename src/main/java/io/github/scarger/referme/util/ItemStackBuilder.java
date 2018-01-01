package io.github.scarger.referme.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

/**
 * Created by Synch on 2017-11-15.
 */
public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;

    public ItemStackBuilder(Material material){
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder id(short id){
        itemStack.setDurability(id);
        return this;
    }

    public ItemStackBuilder name(String name){
        getMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        return this;
    }

    public ItemStack build(){
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ItemMeta getMeta(){
        meta = itemStack.getItemMeta();
        return meta;
    }

    public static ItemStack skuller(UUID uuid){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(short) 3);
        String playerName = Bukkit.getOfflinePlayer(uuid).getName();

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(playerName);
        skull.setItemMeta(skullMeta);

        //access item-meta to change display name to something cleaner
        ItemMeta itemMeta = skull.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA+playerName);
        skull.setItemMeta(itemMeta);

        return skull;
    }

}
