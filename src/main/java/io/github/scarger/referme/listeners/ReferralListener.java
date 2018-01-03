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


import java.util.Map;
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
        final Map<String,String> messages = getPlugin().getConfig().getMessages();
        //players involved in referral
        Player player = event.getPlayer();
        OfflinePlayer referrer = event.getReferrer();

        if(!isEligible(player,referrer)) {
            event.setCancelled(true);
            return;
        }

        player.sendMessage(PREFIX+messages.get("select-referral").replace("%referrer%",referrer.getName()));

        if(referrer.isOnline()){
            ((Player) referrer).sendMessage(PREFIX+messages.get("referral-added")
                    .replace("%player%",player.getName()));

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
            //already referred
            player.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("already-referred"));
        }
        else if(player.getUniqueId().equals(players.getRaw().get(referrer.getUniqueId()).getReferrer())){
            //cross referring
            player.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("cross-referral"));
        }
        else if(toHours(player.getStatistic(Statistic.PLAY_ONE_TICK))
                <getPlugin().getConfig().getHourRequirement()){
            //not enough time
            player.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("require-playtime")
                            .replace("%time_requirement%", Integer.toString(getPlugin().getConfig().getHourRequirement())));
        }
        else if(noPermission){
            player.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("referral-no-permission"));
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
