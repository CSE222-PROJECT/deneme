package Panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class OrganicsMapClass<K,V>  implements Map {
    private ArrayList<K> keyList = new ArrayList<>();
    private ArrayList<V> valueList = new ArrayList<>();

    public OrganicsMapClass() {
    }

    public OrganicsMapClass(K key, V value) {
        if (!keyList.contains(key)) {
            keyList.add(key);
            valueList.add(value);
        }
    }

    @Override
    public int size() {
        return keyList.size();
    }

    @Override
    public boolean isEmpty() {
        return keyList.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keyList.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return valueList.contains(value);
    }

    @Override
    public Object get(Object key) {
        return valueList.get(keyList.indexOf(key));
    }

    @Override
    public Object put(Object key, Object value) {
        if (!keyList.contains(key)) {
            keyList.add((K) key);
            valueList.add((V) value);
            return value;
        }
        return null;
    }

    @Override
    public Object remove(Object key) {
        if (keyList.contains(key)){
            Object temp = key;
            keyList.remove(keyList.indexOf(key));
            valueList.remove(keyList.indexOf(key));

            return key;
        }
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {
        keyList.clear();
        valueList.clear();
    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}