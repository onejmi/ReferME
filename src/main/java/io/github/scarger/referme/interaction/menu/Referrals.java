package io.github.scarger.referme.interaction.menu;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import io.github.scarger.referme.storage.PlayerStorage;
import io.github.scarger.referme.storage.type.StorageMap;
import io.github.scarger.referme.util.ItemStackBuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Synch on 2017-11-15.
 */
public class Referrals extends PluginInjected {

    private Inventory inventory;
    private final PlayerStorage playerStorage;
    //widgets
    private final ItemStack BACKGROUND = new ItemStackBuilder(Material.STAINED_GLASS_PANE).name("*").build();
    private final ItemStack BACK_BUTTON = new ItemStackBuilder(Material.STAINED_GLASS_PANE).id((short) 14).name("&cBACK").build();
    private final ItemStack NEXT_BUTTON = new ItemStackBuilder(Material.STAINED_GLASS_PANE).id((short) 5).name("&aNEXT").build();

    private final List<PlayerStorage> referredPlayers;
    private final int page;

    public Referrals(ReferME plugin, PlayerStorage playerStorage, int page){
        super(plugin);
        this.playerStorage = playerStorage;
        this.page = page;
        this.referredPlayers = getReferred();

        //init inventory
        constructInventory();
        //render..
        render();

    }


    private void constructInventory(){
        inventory = Bukkit.createInventory(null,
                27,
                ChatColor.GOLD+"Referrals "+ChatColor.AQUA+"-> P. "+page);
        //fill the inventory with the background
        for(int slot=0;slot<inventory.getSize();slot++){
            inventory.setItem(slot,BACKGROUND);
        }

        inventory.setItem(9,BACK_BUTTON);
        inventory.setItem(17,NEXT_BUTTON);

    }

    private void render(){
        int currentSlot = 10;

        for(PlayerStorage playerStorage : referredPlayers){
            //render the corresponding skull of playerStorage to the inventory
            inventory.setItem(currentSlot,
                    ItemStackBuilder.skuller(playerStorage.getUUID()));
            currentSlot++;
        }
    }

    private List<PlayerStorage> getReferred(){
        List<PlayerStorage> referredPlayers = new ArrayList<>();

        StorageMap<UUID,PlayerStorage> players = getPlugin().getStorage().getPlayers();
        players.getRaw().values().
                forEach(p -> {
                    PlayerStorage referred = players.getRaw().get(p.getReferrer());
                    if(referred != null && referred.equals(playerStorage))
                        referredPlayers.add(p);
                });

        int startIndex = (page-1)*5;
        int endIndex = startIndex+5;

        return referredPlayers.stream()
                .filter(p -> referredPlayers.indexOf(p)>=startIndex && referredPlayers.indexOf(p)<endIndex)
                .collect(Collectors.toList());
    }

    public Inventory getResult(){
        return inventory;
    }
}
