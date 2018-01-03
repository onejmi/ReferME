package io.github.scarger.referme.message;

import io.github.scarger.referme.storage.ConfigurationStorage;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Synch on 2018-01-02.
 */
public enum MessageDefault {

    NO_PERMS("no-permission", ChatColor.RED+"Insufficient permission"),
    INCORRECT_COMMAND("incorrect-command","Not quite? Use /referme help"),
    HELP_USAGE("help-usage","/referme help"),
    GRABBING_ID("grab-id","Grabbing..."),
    SHOW_ID("show-id","Your ID number is: " + ChatColor.GREEN + "%id");

    private String key;
    private String value;

    MessageDefault(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }

    public static ConfigurationStorage updateMessageStructure(ConfigurationStorage config){
        Set<Map.Entry<String,String>> entries = config.getMessages().entrySet();
        //check if configuration needs updating
        Arrays.stream(values()).forEach(def -> {
                    if(!(entries.stream().map(Map.Entry::getKey)
                            .filter(entry ->
                                    !(Arrays.stream(values())
                                            .map(MessageDefault::getKey)
                                            .collect(Collectors.toList()).contains(entry)))
                            .collect(Collectors.toList())
                            .contains(def.getKey()))) config.getMessages().put(def.getKey(),def.getValue());

                }

        );
        //save updated message section to disk
        return config;
    }
}
