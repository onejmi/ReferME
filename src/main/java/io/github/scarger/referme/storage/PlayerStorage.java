package io.github.scarger.referme.storage;

import io.github.scarger.referme.ReferME;

import java.util.UUID;

/**
 * Created by Synch on 2017-10-29.
 */
public class PlayerStorage {

    private int id;
    private UUID referrer;

    public PlayerStorage(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public UUID getReferrer(){
        return referrer;
    }

    public boolean isReferred(){
        return referrer!=null;
    }

    public void setReferrer(UUID referrer){
        this.referrer = referrer;
        ReferME.get().getJsonStorage().write(ReferME.get().getStorage());
    }
}
