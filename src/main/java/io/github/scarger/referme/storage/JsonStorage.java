package io.github.scarger.referme.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.scarger.referme.Loader;
import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import io.github.scarger.referme.storage.type.JsonSerializable;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Synch on 2017-10-24.
 * little wrapper
 */
public class JsonStorage {

    private File output;
    private Gson gsonInstance;

    JsonStorage(File output){
        this.output = output;
        this.gsonInstance = new GsonBuilder().setVersion(1.0).setPrettyPrinting().create();
    }

    public synchronized boolean write(Object data){
        try(Writer writer = new FileWriter(output)) {
            gsonInstance.toJson(data,writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized Object read(Class<?> structure){
        try(Reader reader = new FileReader(output)) {
            return gsonInstance.fromJson(reader,structure);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class Wrapper extends PluginInjected {
        private File file;
        private JsonStorage storage;
        private Class<? extends JsonSerializable> storageClass;

        public Wrapper(ReferME plugin, String fileName,
                       File folder, Class<? extends JsonSerializable> storageClass){
            super(plugin);
            this.file = getFile(fileName,folder);
            this.storage = new JsonStorage(file);
            this.storageClass = storageClass;
            //ensure storage isn't empty (at least has default implementation)
            populate();
        }

        public Wrapper(ReferME plugin, String fileName, Class<? extends JsonSerializable> storageClass){
            this(plugin,fileName, Loader.getPlugin(Loader.class).getDataFolder(),storageClass);
        }

        private void populate(){
            if(file.length()==0){
                try {
                    if(storageClass.isAssignableFrom(Storage.class)){
                        Constructor<Storage> storageConstructor = Storage.class.getConstructor(ReferME.class);
                        storage.write(storageConstructor.newInstance(getPlugin()));
                    }
                    else {
                        storage.write(storageClass.newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

        public JsonStorage getStorage() {
            return storage;
        }

        public JsonSerializable getStorageSection() {
            return (JsonSerializable) storage.read(storageClass);
        }

    }



}
