package com.jikezhiji.core.structure;

/**
 * Created by E355 on 2016/10/14.
 */
public class Property<V> {
    private String key;
    private V value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property<?> property = (Property<?>) o;

        return getKey().equals(property.getKey());

    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }


    public Property(String key, V value) {
        this.key = key;
        this.value = value;
    }

    public Property() {
    }
}
