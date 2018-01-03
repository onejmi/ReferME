package io.github.scarger.referme.storage;

import com.google.gson.annotations.Expose;
import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import io.github.scarger.referme.storage.type.JsonSerializable;
import io.github.scarger.referme.storage.type.StorageMap;

import java.util.*;

/**
 * Created by Synch on 2017-10-24.
 */
public class Storage extends PluginInjected.Serialized implements JsonSerializable {
    @Expose
    private StorageMap<UUID,PlayerStorage> players;
    @Expose
    private int lastId;

    public Storage(){
        this.players = new StorageMap<>();
        //starting id
        this.lastId = 999;
    }

    @Override
    public void inject(ReferME plugin) {
        players.inject(plugin);
    }

    public StorageMap<UUID,PlayerStorage> getPlayers() {
        return players;
    }

    public synchronized int generateId(){
        return ++lastId;
    }

}
