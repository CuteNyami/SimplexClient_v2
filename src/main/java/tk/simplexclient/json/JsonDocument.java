package tk.simplexclient.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonDocument implements IDocument<JsonDocument> {

    private final Gson gson;

    private final JsonObject object;

    public JsonDocument(Gson gson, JsonObject object) {
        this.gson = gson;
        this.object = object;
    }

    public JsonDocument(JsonObject object) {
        this(new Gson(), object);
    }

    public JsonDocument(String jsonString) {
        this.gson = new Gson();
        this.object = gson.fromJson(jsonString, JsonObject.class);
    }

    public JsonDocument(Gson gson, String jsonString) {
        this(gson, gson.fromJson(jsonString, JsonObject.class));
    }

    public JsonDocument(Gson gson) {
        this(gson, new JsonObject());
    }

    public JsonDocument() {
        this(new Gson(), new JsonObject());
    }

    @Override
    public JsonDocument append(String key, Object value) {
        object.add(key, gson.toJsonTree(value));
        return this;
    }

    @Override
    public JsonDocument depend(String key) {
        object.remove(key);
        return this;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return gson.fromJson(object.get(key), clazz);
    }

    @Override
    public String toString() {
        return gson.toJson(object);
    }
}