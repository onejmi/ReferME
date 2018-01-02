package io.github.scarger.referme;

import io.github.scarger.referme.commands.*;
import io.github.scarger.referme.interaction.ClickHandler;
import io.github.scarger.referme.interaction.handlers.RBButton;
import io.github.scarger.referme.interaction.handlers.RNButton;
import io.github.scarger.referme.listeners.ClickListener;
import io.github.scarger.referme.listeners.JoinListener;
import io.github.scarger.referme.listeners.ReferralListener;
import io.github.scarger.referme.storage.watch.Watcher;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by Synch on 2017-10-12.
 */
public final class Loader extends JavaPlugin{

    private Watcher watcher;
    private ReferME plugin;

    public void onEnable(){
        //init referme class
        this.plugin = new ReferME();
        //log
        getLogger().info("This is a snapshot! It's currently not ready for server environments.");
        //registering components...
        registerCommands();
        registerListeners();
        registerClickHandlers();
        this.watcher = new Watcher(plugin,getDataFolder().getPath());
        watcher.start();
    }

    public void onDisable(){
        getLogger().info("Thanks for using ReferME, please report all bugs @ " +
                ChatColor.AQUA+"https://github.com/scarger/ReferME/issues");
        //terminate watcher
        watcher.close();
        //save storage from memory
        plugin.getJsonStorage().write(plugin.getStorage());
    }

    private void registerCommands(){
        getCommand("referme").setExecutor(new BaseCommand(plugin));
        List<SubCommand> subCommands = plugin.getCommands();
        subCommands.add(new HelpCmd(plugin));
        subCommands.add(new CodeCmd(plugin));
        subCommands.add(new ReferCmd(plugin));
        subCommands.add(new ReloadCmd(plugin));
        subCommands.add(new ReferralsCmd(plugin));
    }

    private void registerListeners(){
        //plugin manager to register events
        PluginManager pluginManager = getServer().getPluginManager();
        //register...
        pluginManager.registerEvents(new JoinListener(plugin),this);
        pluginManager.registerEvents(new ReferralListener(plugin), this);
        pluginManager.registerEvents(new ClickListener(plugin), this);

    }

    private void registerClickHandlers(){
        List<ClickHandler> handlers = plugin.getClickHandlers();
        handlers.add(new RBButton(plugin));
        handlers.add(new RNButton(plugin));
    }
}
