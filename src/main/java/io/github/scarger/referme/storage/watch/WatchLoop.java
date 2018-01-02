package io.github.scarger.referme.storage.watch;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by Synch on 2017-11-05.
 */
public class WatchLoop extends PluginInjected implements Runnable {

    private Path path;

    WatchLoop(ReferME plugin, Path path){
        super(plugin);
        this.path = path;
    }

    @Override
    public void run() {
        Bukkit.getLogger().info("Watcher thread is now starting.");
        WatchKey key;

        try (WatchService service = path.getFileSystem().newWatchService()) {
            //register the path for a certain event so the watcher will watch for it
            path.register(service, ENTRY_MODIFY, ENTRY_DELETE);
            Bukkit.getLogger().info("DEBUG: "+path.toString());
            while (true) {
                try {
                    if (getPlugin().getConfig().isAutoChange()) {

                        key = service.take();
                        WatchEvent.Kind<?> kind;
                        for (WatchEvent<?> watchEvent : key.pollEvents()) {
                            // Get the type of the event
                            kind = watchEvent.kind();
                            if (ENTRY_MODIFY == kind || ENTRY_DELETE == kind) {
                                Bukkit.getLogger().info("Change found, reloading...");
                                getPlugin().initStorage();
                            }
                        }
                        if (!key.reset()) {
                            break;
                        }
                        //sleep, we don't need it to reload instantly.
                    }
                    Thread.sleep(5000);
                }
                catch (InterruptedException e){
                    Bukkit.getLogger().info("Terminating watcher thread");
                    break;
                }
            }

            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
