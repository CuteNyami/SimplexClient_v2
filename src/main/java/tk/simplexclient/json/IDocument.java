package tk.simplexclient.json;

public interface IDocument<D extends IDocument<?>> {

    D append(String key, Object value);

    D depend(String key);

    <T> T get(String key, Class<T> clazz);
}