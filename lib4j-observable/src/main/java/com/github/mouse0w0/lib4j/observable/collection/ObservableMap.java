package com.github.mouse0w0.lib4j.observable.collection;

import java.util.Map;

public interface ObservableMap<K, V> extends Map<K, V> {

    void addChangeListener(MapChangeListener<? super K, ? super V> listener);

    void removeChangeListener(MapChangeListener<? super K, ? super V> listener);
}
