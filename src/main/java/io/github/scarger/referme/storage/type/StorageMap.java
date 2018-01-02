package io.github.scarger.referme.storage.type;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Synch on 2017-11-05.
 */
public class StorageMap<K,V> extends PluginInjected{
    private Map<K,V> rawMap;

    public StorageMap(ReferME plugin){
        super(plugin);
        rawMap = new HashMap<>();
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
        getPlugin().getJsonStorage().write(getPlugin().getStorage());
    }
}
