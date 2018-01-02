package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Synch on 2017-10-14.
 */
public class HelpCmd extends SubCommand {

    public HelpCmd(ReferME plugin) {
        super(plugin,"help", "referme.help",Arrays.asList("page"),false);
    }


    @Override
    void run(CommandSender sender, List<String> args) {
        int page = 1;
        int totalPages = ((int) Math.floor(getPlugin().getCommands().size()/7))+1;
        if(args.size()>=1){
            try {
                page = Integer.parseInt(args.get(0));
                if(page>totalPages || page<1){
                    page = 1;
                }
            }
            catch (NumberFormatException e){
                sender.sendMessage(ChatColor.RED+"That page number is invalid, showing default...");
            }
        }
        //show page
        sender.sendMessage(ChatColor.GOLD+"********** ReferME Help **********");

        //only one page? Just print all the commands in that scenario.
        if(totalPages<=1) {
            getPlugin().getCommands().forEach(cmd -> showCommand(cmd,sender));
        }
        else{
            //loop through all commands corresponding to the page, print(help formatted message)
            int startIndex = (page-1)*7;
            int endIndex = startIndex+6;

            for(int cmd = (page-1)*7; cmd<=endIndex; cmd++){
                showCommand(getPlugin().getCommands().get(cmd),sender);
            }

        }
        //footer
        sender.sendMessage(String.format(ChatColor.GOLD+"*********** Page[%d/%d] ************",page,totalPages));
    }

    private void showCommand(SubCommand cmd, CommandSender sender){
        sender.sendMessage(ChatColor.AQUA + cmd.getUsage() +
                ChatColor.GRAY + " -> " + ChatColor.YELLOW + cmd.getDescription());
    }

    @Override
    String getDescription(){
        return "Info on how to use commands";
    }
}
