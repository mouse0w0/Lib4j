package com.github.mouse0w0.lib4j.observable.collection;

import java.util.*;

public interface ObservableSet<E> extends Set<E> {

    void addChangeListener(SetChangeListener<? super E> listener);

    void removeChangeListener(SetChangeListener<? super E> listener);

    default boolean addAll(E... elements) {
        return Collections.addAll(this, elements);
    }
}
