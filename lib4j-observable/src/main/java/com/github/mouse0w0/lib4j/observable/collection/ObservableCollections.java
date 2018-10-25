package com.github.mouse0w0.lib4j.observable.collection;

import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

public final class ObservableCollections {
    private ObservableCollections() {
    }

    public static <E> ObservableList observableList(List<E> list) {
        return list instanceof RandomAccess ? new ObservableRandomAccessListWrapper(list) : new ObservableListWrapper(list);
    }

    public static <E> ObservableSet<E> observableSet(Set<E> set) {
        return new ObservableSetWrapper<>(set);
    }

    public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
        return new ObservableMapWrapper<>(map);
    }
}
