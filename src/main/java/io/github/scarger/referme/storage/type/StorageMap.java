package io.github.scarger.referme.storage.type;

import com.google.gson.annotations.Expose;
import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Synch on 2017-11-05.
 */
public class StorageMap<K,V> extends PluginInjected.Serialized{
    @Expose
    private Map<K,V> rawMap;

    private transient ReferME plugin;

    public StorageMap(){
        rawMap = new HashMap<>();
    }

    @Override
    public void inject(ReferME plugin) {
        this.plugin = plugin;
        rawMap.values().forEach(v -> ((PluginInjected.Serialized) v).inject(plugin));
    }

    public Map<K,V> getRaw(){
        return rawMap;
    }

    public synchronized void put(K key, V value){
        rawMap.put(key,value);
        save();
    }

    public synchronized void remove(K key){
        rawMap.remove(key);
        save();
    }

    private void save(){
        plugin.getJsonStorage().write(plugin.getStorage());
    }
}
