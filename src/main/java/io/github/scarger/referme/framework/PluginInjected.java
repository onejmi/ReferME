package io.github.scarger.referme.framework;

import io.github.scarger.referme.ReferME;

/**
 * Created by Synch on 2018-01-02.
 */
public abstract class PluginInjected {

    //make sure this doesn't get serialized by accident
    private transient ReferME plugin;

    public PluginInjected(ReferME plugin){
        this.plugin=plugin;
    }

    public ReferME getPlugin(){
        return plugin;
    }
}
