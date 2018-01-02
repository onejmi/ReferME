package io.github.scarger.referme.commands;

import io.github.scarger.referme.ReferME;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Synch on 2017-10-22.
 */
public class CodeCmd extends SubCommand {

    public CodeCmd(ReferME plugin){
        super(plugin,"code","referme.code",true);
    }

    @Override
    void run(CommandSender sender, List<String> args) {
        sender.sendMessage(getPlugin().getConfig().getPrefix()+" Grabbing...");
        sender.sendMessage(getPlugin().getConfig().getPrefix()+"Your ID number is: " + ChatColor.GREEN + getId(sender));
    }

    @Override
    String getDescription() {
        return "Get your referral code";
    }

    private int getId(CommandSender sender){
        if(!(sender instanceof Player)) return 0;
        Player player = (Player) sender;

        return getPlugin().getStorage().getPlayers()
                .getRaw().get(player.getUniqueId()).getId();
    }
}
