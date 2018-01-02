package io.github.scarger.referme.storage.watch;

import io.github.scarger.referme.ReferME;
import io.github.scarger.referme.framework.PluginInjected;

import java.nio.file.*;

/**
 * Created by Synch on 2017-11-05.
 */
public class Watcher extends PluginInjected{

    private Path path;
    private Thread watchThread;

    public Watcher(ReferME plugin, String path){
        super(plugin);
        this.path = Paths.get(path);
    }

    public void start() {
        this.watchThread = new Thread(new WatchLoop(getPlugin(),path));
        watchThread.start();
    }

    public void close(){
        this.watchThread.interrupt();
    }


}
