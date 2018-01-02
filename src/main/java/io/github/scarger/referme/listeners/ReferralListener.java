package io.github.scarger.referme.listeners;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.events.ReferralEvent;
import io.github.scarger.referme.framework.PluginInjected;
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


import java.util.UUID;

/**
 * Created by Synch on 2017-10-29.
 */
public class ReferralListener extends PluginInjected implements Listener{

    public ReferralListener(ReferME plugin) {
        super(plugin);
    }

    @EventHandler (priority = EventPriority.LOWEST) //make sure users of api can override what's done here
    public void onReferral(ReferralEvent event){
        final String PREFIX = getPlugin().getConfig().getPrefix();
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
                    " has added you as their referral, congrats!");

        }

        reward(player,referrer);

    }

    private boolean isEligible(Player player, OfflinePlayer referrer){
        boolean eligible = false;
        boolean noPermission = getPlugin().hasVault()
                && !getPlugin().getPermissionsManager().has(referrer,"referme.refer")
                && !getPlugin().getPermissionsManager().has(referrer,"referme.*")
                && !getPlugin().getPermissionsManager().has(referrer,"*")
                && !referrer.isOp();

        StorageMap<UUID,PlayerStorage> players  = getPlugin().getStorage().getPlayers();

        if(players.getRaw().get(player.getUniqueId()).isReferred()){
            player.sendMessage(getPlugin().getConfig().getPrefix()+ChatColor.RED+"You have already been referred");
        }
        else if(player.getUniqueId().equals(players.getRaw().get(referrer.getUniqueId()).getReferrer())){
            player.sendMessage(getPlugin().getConfig().getPrefix()+ChatColor.RED+"You can't refer someone who referred you");
        }
        else if(toHours(player.getStatistic(Statistic.PLAY_ONE_TICK))
                <getPlugin().getConfig().getHourRequirement()){
            player.sendMessage(getPlugin().getConfig().getPrefix()+ChatColor.RED+"You must play for a total of " +
                    getPlugin().getConfig().getHourRequirement()+"hrs or more before having the ability to refer others");
        }
        else if(noPermission){
            player.sendMessage(getPlugin().getConfig().getPrefix()+ChatColor.RED+"That player doesn't have permission " +
                    "to refer players on this system");
        }
        else{
            eligible = true;
        }

        return eligible;
    }

    private void reward(Player player, OfflinePlayer referrer){
        PlayerStorage referrerStorage = getPlugin().getStorage().getPlayers().getRaw().get(referrer.getUniqueId());
        //prepare commands and run
        getPlugin().getConfig().getRewardCommands().stream()
                .map(cmd -> cmd.replaceAll("%player%",player.getName()))
                .map(cmd -> cmd.replaceAll("%referrer%",referrer.getName()))
                .forEach(cmd -> Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd));
        //hit a milestone?
        if(isAchievement(referrerStorage)){
            getPlugin().getConfig().getAchievements().get(referrerStorage.getReferrals()+1)
                    .stream()
                    .map(cmd -> cmd.replaceAll("%player%",referrer.getName()))
                    .forEach(cmd -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),cmd));
        }
    }

    private boolean isAchievement(PlayerStorage playerStorage){
        return getPlugin().getConfig().getAchievements().keySet().contains(playerStorage.getReferrals()+1);
    }

    private double toHours(int ticks){
        return ((ticks/20)/60)/60;
    }
}
