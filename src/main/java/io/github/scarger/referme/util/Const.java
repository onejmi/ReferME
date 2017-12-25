package io.github.scarger.referme.util;

import com.avaje.ebean.validation.NotNull;
import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.storage.ConfigurationStorage;
import org.bukkit.ChatColor;

/**
 * Created by Synch on 2017-10-14.
 */

public enum  Const {
    
    ISSUE_LINK("https://github.com/scarger/ReferME/issues"),
    NO_PERM(ChatColor.RED+"Insufficient permission");

    private String value;

    Const(String value){
        this.value=value;
    }

    @NotNull
    public String getValue() {
        return value;
    }

}
