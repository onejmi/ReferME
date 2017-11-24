package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.events.ReferralEvent;
import io.github.scarger.referme.util.Const;
import io.github.scarger.referme.util.RCommons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Synch on 2017-10-27.
 */
public class ReferCmd extends SubCommand {

    public ReferCmd(){
        super("refer",Arrays.asList("code"),true,true);
    }

    @Override
    void run(CommandSender sender, List<String> args) {
        int idCode;

        try {
            idCode = Integer.parseInt(args.get(0));
        }
        catch (NumberFormatException e){
            sender.sendMessage(ReferME.get().getConfig().getPrefix()+ ChatColor.RED+"Please specify a valid integer as the id");
            return;
        }

        if(ReferME.get().getStorage().getPlayers().getRaw().get(((Player) sender).getUniqueId()).getId() != idCode){
            tryReferral(sender, idCode);
        }
        else{
            sender.sendMessage(ReferME.get().getConfig().getPrefix()+ ChatColor.RED+"You can't refer yourself!");
        }

    }

    @Override
    String getDescription() {
        return "Specify who brought you to the server (using their #id)";
    }


    private void tryReferral(CommandSender sender, int idCode){
        Player player = (Player) sender;
        UUID referralUUID = RCommons.getKey(idCode);

        if(referralUUID == null){
            sender.sendMessage(ReferME.get().getConfig().getPrefix()+ ChatColor.RED+"That id doesn't exist");
            return;
        }

        ReferralEvent referralEvent = new ReferralEvent((Player) sender, Bukkit.getOfflinePlayer(referralUUID));

        //call the referral event
        Bukkit.getServer().getPluginManager()
                .callEvent(referralEvent);

        if(!(referralEvent.isCancelled())) {
            saveReferral(player.getUniqueId(),referralUUID);
        }
    }

    //saving method(to lower clutter..)
    private void saveReferral(UUID playerUUID, UUID referralUUID){
        ReferME.get().getStorage().getPlayers()
                .getRaw().get(playerUUID).setReferrer(referralUUID);
    }
}
