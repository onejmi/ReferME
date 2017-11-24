package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.github.scarger.referme.util.Const;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Synch on 2017-10-14.
 */
public abstract class SubCommand {

    private String name;
    private List<String> args;
    private boolean argLimit;
    private boolean onlyPlayer;

    public SubCommand(String name, List<String> args, boolean argLimit, boolean onlyPlayer){
        this.name = name;
        this.args = args;
        this.argLimit = argLimit;
        this.onlyPlayer = onlyPlayer;
    }

    public SubCommand(String name, List<String> args, boolean argLimit){
        this(name,args,argLimit,false);
    }

    public SubCommand(String name, boolean onlyPlayer){
        this(name,Arrays.asList("none"),false,onlyPlayer);
    }

    public SubCommand(String name){
        this(name, Arrays.asList("none"),false,false);
    }

    public void runCmd(CommandSender sender, String[] fullArgs){
        if(onlyPlayer && !(sender instanceof Player)){
            sender.sendMessage(ReferME.get().getConfig().getPrefix()+ChatColor.RED+"Sorry! only players can do that.");
        }
        else{
            if((args.size()!=(fullArgs.length-1)) && argLimit){
                sender.sendMessage(ReferME.get().getConfig().getPrefix()+getUsage());
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
