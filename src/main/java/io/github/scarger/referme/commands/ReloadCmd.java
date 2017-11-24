package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.List;

/**
 * Created by Synch on 2017-11-03.
 */
public class ReloadCmd extends SubCommand {

    public ReloadCmd(){
        super("reload",false);
    }

    @Override
    void run(CommandSender sender, List<String> args) {
        sender.sendMessage(ReferME.get().getConfig().getPrefix()+"Reloading...");
        ReferME.get().initStorage();
        sender.sendMessage(ReferME.get().getConfig().getPrefix()+ ChatColor.GREEN+"Successfully reloaded plugin");
    }

    @Override
    String getDescription() {
        return "Load changes(config,data,etc.) into memory";
    }
}