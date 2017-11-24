package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.util.Const;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Synch on 2017-10-14.
 */
public class BaseCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("referme")){
            if(args.length>=1) {
                boolean hasCommand = false;
                for (SubCommand cmd : ReferME.get().getCommands()) {
                    if (args[0].equalsIgnoreCase(cmd.getName())) {
                        cmd.runCmd(commandSender, args);
                        hasCommand = true;
                        break;
                    }
                }
                if(!hasCommand){
                    commandSender.sendMessage(ReferME.get().getConfig().getPrefix()+"Not quite? Use /referme help");
                }
            }
            else{
                commandSender.sendMessage(ReferME.get().getConfig().getPrefix()+"/referme help");
            }
        }

        return false;
    }
}
