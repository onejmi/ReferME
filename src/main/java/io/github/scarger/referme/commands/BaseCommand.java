package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import io.github.scarger.referme.util.Const;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Synch on 2017-10-14.
 */
public class BaseCommand extends PluginInjected implements CommandExecutor{

    public BaseCommand(ReferME plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("referme")){
            if(args.length>=1) {
                boolean hasCommand = false;
                for (SubCommand cmd : getPlugin().getCommands()) {
                    if (args[0].equalsIgnoreCase(cmd.getName())) {
                        if(!hasPermission(commandSender,cmd)){
                            commandSender.sendMessage(Const.NO_PERM.getValue());
                            hasCommand = true;
                            break;
                        }
                        cmd.runCmd(commandSender, args);
                        hasCommand = true;
                        break;
                    }
                }
                if(!hasCommand){
                    commandSender.sendMessage(getPlugin().getConfig().getPrefix()+"Not quite? Use /referme help");
                }
            }
            else{
                commandSender.sendMessage(getPlugin().getConfig().getPrefix()+"/referme help");
            }
        }

        return false;
    }

    private boolean hasPermission(CommandSender commandSender, SubCommand cmd){
        return commandSender.hasPermission(cmd.getPermission())
                || commandSender.hasPermission("referme.*")
                || commandSender.hasPermission("*")
                || commandSender.isOp();
    }
}
