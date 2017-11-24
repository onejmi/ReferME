package io.github.scarger.referme.storage;

import com.google.gson.annotations.Since;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Synch on 2017-11-01.
 */
public final class ConfigurationStorage {
    private @Since(0.1) String prefix;
    private @Since(0.1) boolean autoChange;
    private @Since(0.1) int hourRequirement;

    private @Since(0.1) List<String> rewardCommands;

    public ConfigurationStorage(){
        //looks dirtier than making a transient field, but better practice to make it local(when possible)
        final String[] DEFAULT_COMMANDS =
                {"me welcome %player%!","me %referrer% gj for bringing %player%","give %player% diamond"};

        this.prefix = "&e[ReferME] &b";
        this.autoChange = true;
        this.hourRequirement = 3;
        this.rewardCommands =
                Arrays.asList(DEFAULT_COMMANDS);
    }

    public String getPrefix(){
        return ChatColor.translateAlternateColorCodes('&',prefix);
    }

    public boolean isAutoChange(){
        return autoChange;
    }

    public int getHourRequirement(){
        return hourRequirement;
    }

    public List<String> getRewardCommands(){
        return rewardCommands;
    }
}
