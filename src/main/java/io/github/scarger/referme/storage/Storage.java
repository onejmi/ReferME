package io.github.scarger.referme.storage;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.storage.type.JsonSerializable;
import io.github.scarger.referme.storage.type.StorageMap;

import java.util.*;

/**
 * Created by Synch on 2017-10-24.
 */
public class Storage implements JsonSerializable {

    private StorageMap<UUID,PlayerStorage> players;
    private int lastId;

    public Storage(){
        this.players = new StorageMap<>();
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
