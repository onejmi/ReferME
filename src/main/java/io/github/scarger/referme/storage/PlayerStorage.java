package io.github.scarger.referme.storage;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.storage.type.JsonSerializable;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Synch on 2017-10-29.
 */
public class PlayerStorage {

    private int id;
    private UUID referrer;
    private transient UUID uuid;

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

    public UUID getUUID(){
        if(uuid==null) {
            for (Map.Entry<UUID, PlayerStorage> entry : ReferME.get().getStorage().getPlayers().getRaw().entrySet()) {
                if (entry.getValue().getId() == id) {
                    this.uuid=entry.getKey();
                    return entry.getKey();
                }
            }
            throw new IllegalStateException("PlayerStorage does not have UUID on global cache!");
        }
        else{
            return uuid;
        }
    }

    public int getReferrals(){
        int referralCount = 0;
        for(PlayerStorage playerStorage : ReferME.get().getStorage().getPlayers().getRaw().values()){
            if(playerStorage.getReferrer().equals(getUUID())){
                referralCount++;
            }
        }
        return referralCount;
    }
}
