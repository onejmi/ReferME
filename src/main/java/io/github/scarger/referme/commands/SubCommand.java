package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Synch on 2017-10-14.
 */
public abstract class SubCommand extends PluginInjected{

    private String name;
    private String permission;
    private List<String> args;
    private boolean argLimit;
    private boolean onlyPlayer;

    public SubCommand(ReferME plugin, String name, String permission, List<String> args, boolean argLimit, boolean onlyPlayer){
        super(plugin);
        this.name = name;
        this.permission=permission;
        this.args = args;
        this.argLimit = argLimit;
        this.onlyPlayer = onlyPlayer;
    }

    public SubCommand(ReferME plugin, String name, String permission, List<String> args, boolean argLimit){
        this(plugin,name,permission,args,argLimit,false);
    }

    public SubCommand(ReferME plugin, String name, String permission, boolean onlyPlayer){
        this(plugin,name,permission,Arrays.asList("none"),false,onlyPlayer);
    }

    public SubCommand(ReferME plugin, String name, String permission){
        this(plugin,name,permission,Arrays.asList("none"),false,false);
    }

    public void runCmd(CommandSender sender, String[] fullArgs){
        if(onlyPlayer && !(sender instanceof Player)){
            sender.sendMessage(getPlugin().getConfig().getPrefix()+
                    getPlugin().getConfig().getMessages().get("players-only"));
        }
        else{
            if((args.size()!=(fullArgs.length-1)) && argLimit){
                sender.sendMessage(getPlugin().getConfig().getPrefix()+getUsage());
            }
            else{
                run(sender,
                        fullArgs.length >= 2 ? Arrays.asList(fullArgs).subList(1,fullArgs.length) : new ArrayList<>());
            }
        }
    }

    public String getName(){
        return name;
    }

    public String getPermission(){
        return permission;
    }

    public String getUsage(){
        return "/referme "+name+" "+args
                .stream()
                .filter(s -> !(s.contains("none")))
                .map(s -> String.format("[%s]",s))
                .reduce((last,next) -> String.format("%s %s",last,next))
                .orElse("");

    }

    abstract void run(CommandSender sender, List<String> args);

    abstract String getDescription();


}
