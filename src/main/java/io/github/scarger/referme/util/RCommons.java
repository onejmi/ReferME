package io.github.scarger.referme.util;


import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.storage.PlayerStorage;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Synch on 2017-10-27.
 */
public class RCommons {

    @Nullable
    public static UUID getKey(int value){
        for(Map.Entry<UUID,PlayerStorage> entry : ReferME.get().getStorage().getPlayers().getRaw().entrySet()){
            if(entry.getValue().getId() == value){
                return entry.getKey();
            }
        }
        return null;
    }

}
