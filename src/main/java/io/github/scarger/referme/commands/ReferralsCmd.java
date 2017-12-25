package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.wrappers.Referrals;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Synch on 2017-11-08.
 */
public class ReferralsCmd extends SubCommand{

    public ReferralsCmd(){
        super("referrals","referme.referrals",true);
    }

    @Override
    void run(CommandSender sender, List<String> args) {
        Player player = (Player) sender;

        player.openInventory(new Referrals(
                ReferME.get().getStorage().getPlayers().getRaw().get(player.getUniqueId()),
                1
        ).getResult());
    }

    @Override
    String getDescription() {
        return "List all players you have referred";
    }
}
