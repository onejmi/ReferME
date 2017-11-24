package io.github.scarger.referme.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Synch on 2017-10-27.
 */
public class ReferralEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private OfflinePlayer referrer;
    private boolean cancelled;

    public ReferralEvent(Player player, OfflinePlayer referrer) {
        this.player = player;
        this.referrer = referrer;
        this.cancelled = false;
    }

    public Player getPlayer(){
        return player;
    }

    public OfflinePlayer getReferrer(){
        return referrer;
    }

    public boolean isCancelled(){
        return cancelled;
    }

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
