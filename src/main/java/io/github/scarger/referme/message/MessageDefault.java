package io.github.scarger.referme.message;

import com.google.gson.Gson;
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
    SHOW_ID("show-id","Your ID number is: " + ChatColor.GREEN + "%id%"),
    INCORRECT_ID("incorrect-id",ChatColor.RED+"That id doesn't exist"),
    INVALID_NUMBER("invalid-number",ChatColor.RED+"Please specify a valid integer as the id"),
    SELF_REFERRAL("self-referral",ChatColor.RED+"You can't refer yourself!"),
    INVALID_HELP_PAGE("invalid-help-page",ChatColor.RED+"That page number is invalid, showing default..."),
    RELOADING("reloading","Reloading..."),
    RELOAD_SUCCESS("reload-success",ChatColor.GREEN+"Successfully reloaded plugin"),
    PLAYERS_ONLY("players-only",ChatColor.RED+"Sorry! only players can do that."),
    SELECT_REFERRAL("select-referral",ChatColor.GREEN+"You have selected %referrer% as your referrer"),
    REFERRAL_ADDED("referral-added",ChatColor.LIGHT_PURPLE+"%player%"+ChatColor.AQUA +
            " has added you as their referral, congrats!"),
    ALREADY_REFERRED("already-referred",ChatColor.RED+"You have already been referred"),
    CROSS_REFERRAL("cross-referral",ChatColor.RED+"You can't refer someone who referred you"),
    REQUIRE_PLAYTIME("require-playtime",ChatColor.RED+"You must play for a total of %time_requirement% hrs " +
            "or more before having the ability to refer others"),
    REFERRAL_NO_PERMISSION("referral-no-permission",ChatColor.RED+"That player doesn't have permission " +
            "to refer players on this system");

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

    public static ConfigurationStorage updateMessageStructure(ConfigurationStorage config, Gson gsonInstance){
        Set<Map.Entry<String,String>> entries = config.getMessages().entrySet();
        //create a clone
        ConfigurationStorage updatedConfig =
                gsonInstance.fromJson(gsonInstance.toJson(config,ConfigurationStorage.class),ConfigurationStorage.class);
        //check if configuration needs updating
        Arrays.stream(values()).forEach(def -> {
                    if(!(entries.stream().map(Map.Entry::getKey)
                            .filter(entry ->
                                    Arrays.stream(values())
                                            .map(MessageDefault::getKey)
                                            .collect(Collectors.toList()).contains(entry))
                            .collect(Collectors.toList())
                            .contains(def.getKey()))) updatedConfig.getMessages().put(def.getKey(),def.getValue());

                }

        );
        //save updated message section to disk
        return updatedConfig;
    }
}
