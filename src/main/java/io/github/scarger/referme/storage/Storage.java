package io.github.scarger.referme.storage;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import io.github.scarger.referme.storage.type.JsonSerializable;
import io.github.scarger.referme.storage.type.StorageMap;

import java.util.*;

/**
 * Created by Synch on 2017-10-24.
 */
public class Storage extends PluginInjected implements JsonSerializable {

    private StorageMap<UUID,PlayerStorage> players;
    private int lastId;

    public Storage(ReferME plugin){
        super(plugin);
        this.players = new StorageMap<>(getPlugin());
        //starting id
        this.lastId = 999;
    }

    public StorageMap<UUID,PlayerStorage> getPlayers() {
        return players;
    }

    public synchronized int generateId(){
        return ++lastId;
    }
}
