package io.github.scarger.referme.wrappers;


import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Created by Synch on 2017-12-24.
 */
public class PermissionsManager {

    private Permission rawPermission;

    public PermissionsManager(Permission rawPermission){
        this.rawPermission = rawPermission;
    }

    public Permission getRaw() {
        return rawPermission;
    }

    public boolean has(OfflinePlayer offlinePlayer, String permission){
        return rawPermission.playerHas(
                Bukkit.getServer().getWorlds().get(0).getName(),
                offlinePlayer,
                permission
        );
    }
}
