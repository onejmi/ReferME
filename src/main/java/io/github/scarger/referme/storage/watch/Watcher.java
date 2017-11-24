package io.github.scarger.referme.storage.watch;

import io.github.scarger.referme.Loader;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

/**
 * Created by Synch on 2017-11-05.
 */
public class Watcher{

    private Path path;
    private Thread watchThread;

    public Watcher(String path){
        this.path = Paths.get(path);
    }

    public void start() {
        this.watchThread = new Thread(new WatchLoop(path));
        watchThread.start();
    }

    public void close(){
        this.watchThread.interrupt();
    }


}
