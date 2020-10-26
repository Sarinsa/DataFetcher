package com.sarinsa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Supplier;

public class JsonWriter<T> {

    private final Supplier<T> dataSupplier;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonWriter(Class<T> clazz) {
        this.dataSupplier = createDataHolder(clazz);
    }

    public String write() {
        T dataHolder = this.getData();
        return gson.toJson(dataHolder);
    }


    public T getData() {
        return this.dataSupplier.get();
    }

    private Supplier<T> createDataHolder(Class<T> clazz) {
        return () -> {
            try {
                return clazz.newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
