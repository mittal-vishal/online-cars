package com.intuit.craft.onlinecars.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GsonUtils {

    private final Gson gson = new Gson();

    public <T> JsonObject convertToJsonObject(T object) {
        return gson.toJsonTree(object).getAsJsonObject();
    }

    public <T> T readValueFromString(String data, TypeToken<? extends T> typeToken) {
        return gson.fromJson(data, typeToken.getType());
    }

    public <T> T readValueFromJson(JsonElement data, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    public <T> String convertToJsonString(T object) {
        return gson.toJson(object);
    }

    public <T> JsonElement convertToJsonElement(T object){
        return gson.toJsonTree(object);
    }

}
