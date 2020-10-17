package com.sarinsa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter<T> {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonWriter(Class<T> clazz) {

    }

    public void write() {

    }
}
