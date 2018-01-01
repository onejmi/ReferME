package io.github.scarger.referme.interaction.handlers;

import io.github.scarger.referme.interaction.ClickHandler;
import io.github.scarger.referme.interaction.MenuType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Synch on 2017-11-18.
 */
public class Button extends ClickHandler {

    public Button(MenuType type, int slot) {
        super(type, slot);
    }

    @Override
    public void handle(Player player, Inventory inventory) {/*nothing special here*/}
}
