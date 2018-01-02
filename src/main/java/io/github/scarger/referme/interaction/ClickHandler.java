package io.github.scarger.referme.interaction;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Synch on 2017-11-18.
 */
public abstract class ClickHandler extends PluginInjected {

    private MenuType type;
    private int slot;

    public ClickHandler(ReferME plugin, MenuType type , int slot){
        super(plugin);
        this.type = type;
        this.slot = slot;
    }

    private MenuType getType() {
        return type;
    }

    private int getSlot(){
        return slot;
    }

    public boolean equals(ClickHandler handler){
        return handler.getSlot()==this.slot && handler.getType().equals(this.type);
    }

    public abstract void handle(Player player, Inventory inventory);

}
