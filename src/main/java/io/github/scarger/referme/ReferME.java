package io.github.scarger.referme;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.scarger.referme.commands.SubCommand;
import io.github.scarger.referme.interaction.ClickHandler;
import io.github.scarger.referme.storage.ConfigurationStorage;
import io.github.scarger.referme.storage.JsonStorage;
import io.github.scarger.referme.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Synch on 2017-10-14.
 */
public class ReferME {

    //global instance var
    private static ReferME instance;

    //other vars..
    private List<SubCommand> commands;
    private List<ClickHandler> clickHandlers;
    private JsonStorage jsonStorage;
    private JsonStorage jsonConfiguration;
    private Storage storage;
    private ConfigurationStorage configurationStorage;

    private ReferME(){
        initStorage();
        //to add any updates from new versions
        jsonConfiguration.write(configurationStorage);

        clickHandlers = new ArrayList<>();
        commands = new ArrayList<>();
    }

    public static ReferME get(){
        if(instance==null){
            instance =new ReferME();
        }
        return instance;
    }

    public void initStorage(){
        File folder = Loader.getPlugin(Loader.class).getDataFolder();
        if(!folder.exists()){
            folder.mkdirs();
        }

        File jsonLoc = getFile("data.json",folder);
        File confLoc = getFile("config.json",folder);

        jsonStorage = new JsonStorage(jsonLoc);
        jsonConfiguration = new JsonStorage(confLoc);

        //empty?
        if(jsonLoc.length()==0){ jsonStorage.write(new Storage());}

        if(confLoc.length()==0) {
            jsonConfiguration.write(new ConfigurationStorage());
        }


        //deserialize info...
        storage = (Storage) jsonStorage.read(Storage.class);
        configurationStorage = (ConfigurationStorage) jsonConfiguration.read(ConfigurationStorage.class);


    }

    private File getFile(String name, File folder){
        File file = new File(folder,name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public JsonStorage getJsonStorage() {
        return jsonStorage;
    }

    public Storage getStorage(){
        return storage;
    }

    public ConfigurationStorage getConfig(){
        return configurationStorage;
    }

    public List<SubCommand> getCommands(){
        if(commands==null){
            commands = new ArrayList<>();
        }
        return commands;
    }

    public List<ClickHandler> getClickHandlers(){
        return clickHandlers;
    }
}
