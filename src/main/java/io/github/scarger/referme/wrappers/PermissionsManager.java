package io.github.scarger.referme.wrappers;


//import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Created by Synch on 2017-12-24.
 */
public class PermissionsManager {

    private RegisteredServiceProvider<?> rawPermission;
    private Class permsClass;
    private boolean hasVault;

    public PermissionsManager(String classLoc){
        try {
            this.permsClass = Class.forName(classLoc);
            rawPermission = Bukkit.getServer().getServicesManager().getRegistration(permsClass);
            hasVault = rawPermission!=null;
        }
        //no vault
        catch (ClassNotFoundException e) {
            hasVault = false;
        }
    }

    public RegisteredServiceProvider<?> getRaw() {
        return rawPermission;
    }

    public boolean has(OfflinePlayer offlinePlayer, String permission){
        try {
            return (boolean) permsClass.getClass()
                    .getDeclaredMethod("has", World.class, OfflinePlayer.class, String.class)
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
