package io.github.scarger.referme.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by Synch on 2017-10-24.
 * little wrapper
 */
public class JsonStorage {

    private File output;
    private Gson gsonInstance;

    public JsonStorage(File output){
        this.output = output;
        this.gsonInstance = new GsonBuilder().setVersion(0.1).setPrettyPrinting().create();
    }

    public synchronized boolean write(Object data){
        try(Writer writer = new FileWriter(output)) {
            gsonInstance.toJson(data,writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized Object read(Class<?> structure){
        try(Reader reader = new FileReader(output)) {
            return gsonInstance.fromJson(reader,structure);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



}
