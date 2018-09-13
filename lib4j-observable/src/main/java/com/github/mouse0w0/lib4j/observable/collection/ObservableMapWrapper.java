package com.github.mouse0w0.lib4j.observable.collection;

import java.util.AbstractMap;
import java.util.Set;

public class ObservableMapWrapper<K,V> extends AbstractMap<K, V> implements ObservableMap<K, V> {

    @Override
    public void addChangeListener(MapChangeListener<? super K, ? super V> listener) {

    }

    @Override
    public void removeChangeListener(MapChangeListener<? super K, ? super V> listener) {

    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
