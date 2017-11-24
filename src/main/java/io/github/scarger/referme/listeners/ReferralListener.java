package io.github.scarger.referme.listeners;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.events.ReferralEvent;
import io.github.scarger.referme.storage.PlayerStorage;
import io.github.scarger.referme.storage.type.StorageMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


import java.sql.Ref;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Synch on 2017-10-29.
 */
public class ReferralListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST) //make sure users of api can override what's done here
    public void onReferral(ReferralEvent event){
        final String PREFIX = ReferME.get().getConfig().getPrefix();
        Player player = event.getPlayer();
        OfflinePlayer referrer = event.getReferrer();

        if(!isEligible(player,referrer)) {
            event.setCancelled(true);
            return;
        }

        player.sendMessage(PREFIX+ChatColor.GREEN+"You have selected " +
            referrer.getName()+" as your referrer");

        if(referrer.isOnline()){
            ((Player) referrer).sendMessage(PREFIX+ChatColor.LIGHT_PURPLE+player.getName()+ChatColor.AQUA +
                    " has added you as his referral, congrats!");

        }

        reward(player,referrer);

    }

    private boolean isEligible(Player player, OfflinePlayer referrer){
        StorageMap<UUID,PlayerStorage> players  = ReferME.get().getStorage().getPlayers();
        if(players.getRaw().get(player.getUniqueId()).isReferred()){
            player.sendMessage(ReferME.get().getConfig().getPrefix()+ChatColor.RED+"You have already been referred");
           return false;
        }
        else if(player.getUniqueId().equals(players.getRaw().get(referrer.getUniqueId()).getReferrer())){
            player.sendMessage(ReferME.get().getConfig().getPrefix()+ChatColor.RED+"You can't refer someone who referred you");
           return false;
        }
        else if(toHours(player.getStatistic(Statistic.PLAY_ONE_TICK))
                <ReferME.get().getConfig().getHourRequirement()){
            player.sendMessage(ReferME.get().getConfig().getPrefix()+ChatColor.RED+"You must play for a total of " +
                    ReferME.get().getConfig().getHourRequirement()+"hrs or more before having the ability to refer others");
            return false;
        }
        return true;
    }

    private void reward(Player player, OfflinePlayer referrer){
        ReferME.get().getConfig().getRewardCommands().stream()
                .map(cmd -> cmd.replaceAll("%player%",player.getName()))
                .map(cmd -> cmd.replaceAll("%referrer%",referrer.getName()))
                .forEach(cmd -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd));
    }

    private double toHours(int ticks){
        return ((ticks/20)/60)/60;
    }
}
