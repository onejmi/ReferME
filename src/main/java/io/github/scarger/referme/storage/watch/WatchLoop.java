package io.github.scarger.referme.storage.watch;

import io.github.scarger.referme.ReferME;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by Synch on 2017-11-05.
 */
public class WatchLoop implements Runnable {

    private Path path;

    WatchLoop(Path path){
        this.path = path;
    }

    @Override
    public void run() {
        System.out.println("Watcher thread is now starting.");
        WatchKey key;

        try (WatchService service = path.getFileSystem().newWatchService()) {
            //register the path for a certain event so the watcher will watch for it
            path.register(service, ENTRY_MODIFY, ENTRY_DELETE);
            System.out.println("DEBUG: "+path.toString());
            while (true) {
                try {
                    if (ReferME.get().getConfig().isAutoChange()) {

                        key = service.take();
                        WatchEvent.Kind<?> kind;
                        for (WatchEvent<?> watchEvent : key.pollEvents()) {
                            // Get the type of the event
                            kind = watchEvent.kind();
                            if (ENTRY_MODIFY == kind || ENTRY_DELETE == kind) {
                                System.out.println("Change found, reloading...");
                                ReferME.get().initStorage();
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
                    System.out.println("Terminating Watcher Thread");
                    break;
                }
            }

            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
