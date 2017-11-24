package io.github.scarger.referme;

import io.github.scarger.referme.commands.*;
import io.github.scarger.referme.interaction.ClickHandler;
import io.github.scarger.referme.interaction.handlers.RBButton;
import io.github.scarger.referme.interaction.handlers.RNButton;
import io.github.scarger.referme.listeners.ClickListener;
import io.github.scarger.referme.listeners.JoinListener;
import io.github.scarger.referme.listeners.ReferralListener;
import io.github.scarger.referme.storage.watch.Watcher;
import io.github.scarger.referme.util.Const;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by Synch on 2017-10-12.
 */
public final class Loader extends JavaPlugin{

    private Watcher watcher;

    public void onEnable(){
        getLogger().info("This is a snapshot! It's currently not ready for server environments.");
        registerCommands();
        registerListeners();
        registerClickHandlers();
        this.watcher = new Watcher(getDataFolder().getPath());
        watcher.start();
    }

    public void onDisable(){
        getLogger().info("Thanks for using me, please report all bugs ".concat(Const.ISSUE_LINK.getValue()));
        //terminate watcher
        watcher.close();
        //save storage from memory
        ReferME.get().getJsonStorage().write(ReferME.get().getStorage());
    }

    private void registerCommands(){
        getCommand("referme").setExecutor(new BaseCommand());
        List<SubCommand> subCommands = ReferME.get().getCommands();
        subCommands.add(new HelpCmd());
        subCommands.add(new CodeCmd());
        subCommands.add(new ReferCmd());
        subCommands.add(new ReloadCmd());
        subCommands.add(new ReferralsCmd());
    }

    private void registerListeners(){
        //plugin manager to register events
        PluginManager pluginManager = getServer().getPluginManager();
        //register...
        pluginManager.registerEvents(new JoinListener(),this);
        pluginManager.registerEvents(new ReferralListener(), this);
        pluginManager.registerEvents(new ClickListener(), this);

    }

    private void registerClickHandlers(){
        List<ClickHandler> handlers = ReferME.get().getClickHandlers();
        handlers.add(new RBButton());
        handlers.add(new RNButton());
    }
}
