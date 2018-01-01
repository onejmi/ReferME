package io.github.scarger.referme.management;


//import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Synch on 2017-12-24.
 */
public class PermissionsManager {

    private RegisteredServiceProvider<?> rawPermission;
    private Map<String,Method> methodCache;

    public PermissionsManager(String classLoc){
        methodCache = new HashMap<>();
        try {
            Class<?> permsClass = Class.forName(classLoc);
            rawPermission = Bukkit.getServer().getServicesManager().getRegistration(permsClass);
            methodCache.put("hasOffline",permsClass.getDeclaredMethod("has",World.class,OfflinePlayer.class,String.class));
        }
        //no vault
        catch (Exception e) {/*goto ReferME for message*/}
    }

    public RegisteredServiceProvider<?> getRaw() {
        return rawPermission;
    }

    public boolean has(OfflinePlayer offlinePlayer, String permission){
        try {
            return (boolean) methodCache.get("hasOffline")
                    .invoke(rawPermission.getProvider(),
                            Bukkit.getServer().getWorlds().get(0).getName(),
                            offlinePlayer,
                            permission);
        }
        //too many individual exceptions to catch
        catch (Exception e){
            return false;
        }
    }
}
