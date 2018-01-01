package io.github.scarger.referme.storage;

import com.google.gson.annotations.Since;
import io.github.scarger.referme.storage.type.JsonSerializable;
import org.bukkit.ChatColor;

import java.util.*;

/**
 * Created by Synch on 2017-11-01.
 */
public final class ConfigurationStorage implements JsonSerializable{
    private @Since(1.0) String prefix;
    private @Since(1.0) boolean autoChange;
    private @Since(1.0) int hourRequirement;

    private @Since(1.0) List<String> rewardCommands;
    private @Since(1.0) Map<Integer,List<String>> achievements;

    public ConfigurationStorage(){
        final String[] DEFAULT_COMMANDS = {"me welcome %player%!", "me %referrer% gj for bringing %player%", "give %player% diamond"};
        //more init
        this.prefix = "&e[ReferME] &b";
        this.autoChange = true;
        this.hourRequirement = 3;
        this.rewardCommands =
                Arrays.asList(DEFAULT_COMMANDS);
        this.achievements = new HashMap<>();
        //setup any defaults that can't be instantiated on instance creation
        populateDefaults();
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

    public Map<Integer,List<String>> getAchievements(){return achievements;}

    private void populateDefaults(){
        achievements.put(10, Collections.singletonList("broadcast %player% has brought 10 people to the server, wow!"));
    }
}
