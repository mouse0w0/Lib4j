package com.github.mouse0w0.lib4j.observable.collection;

import java.util.List;

public interface ObservableList<E> extends List<E> {

    void addChangeListener(ListChangeListener<? super E> listener);

    void removeChangeListener(ListChangeListener<? super E> listener);
}
