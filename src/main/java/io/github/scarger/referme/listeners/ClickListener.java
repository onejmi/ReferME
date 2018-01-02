package io.github.scarger.referme.listeners;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.interaction.ClickHandler;
import io.github.scarger.referme.interaction.MenuType;
import io.github.scarger.referme.interaction.handlers.Button;
import io.github.scarger.referme.framework.PluginInjected;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Synch on 2017-11-18.
 */
public class ClickListener extends PluginInjected implements Listener {

    public ClickListener(ReferME plugin) {
        super(plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        String inventoryName = event.getInventory().getName();

        if(delegate(new Button(getPlugin(),filter(inventoryName),event.getSlot()),
                (Player) event.getWhoClicked(), event.getClickedInventory())){
            event.setCancelled(true);
        }
        else if(!(filter(inventoryName).equals(MenuType.NON))){
            event.setCancelled(true);
        }



    }

    private MenuType filter(String inventoryName){
        for(MenuType type : MenuType.values()){
            if(inventoryName.contains(type.getShouldContain())){
                return type;
            }
        }
        return MenuType.NON;
    }

    private boolean delegate(ClickHandler handler, Player player, Inventory inventory){
        for(ClickHandler currHandler : getPlugin().getClickHandlers()){
            if(currHandler.equals(handler)){
                currHandler.handle(player,inventory);
                return true;
            }
        }
        return false;
    }
}
