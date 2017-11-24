package io.github.scarger.referme.interaction.handlers;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.interaction.ClickHandler;
import io.github.scarger.referme.interaction.MenuType;
import io.github.scarger.referme.storage.PlayerStorage;
import io.github.scarger.referme.wrappers.Referrals;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Synch on 2017-11-18.
 */
public class RBButton extends ClickHandler {
    public RBButton() {
        super(MenuType.REFERRALS, 9);
    }

    @Override
    public void handle(Player player, Inventory inventory) {
        PlayerStorage playerStorage = ReferME.get().getStorage().getPlayers().getRaw().get(player.getUniqueId());

        int page = getNumber(inventory.getName())>1 ? getNumber(inventory.getName())-1 : 1;

        player.openInventory(new Referrals(playerStorage,page).getResult());
    }

    private int getNumber(String inventoryName){
        return Integer.parseInt(inventoryName.substring(inventoryName.indexOf("P.")+3));
    }
}
