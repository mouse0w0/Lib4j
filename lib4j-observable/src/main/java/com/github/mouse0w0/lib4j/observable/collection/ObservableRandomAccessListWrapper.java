package com.github.mouse0w0.lib4j.observable.collection;

import java.util.List;
import java.util.RandomAccess;

public class ObservableRandomAccessListWrapper<E> extends ObservableListWrapper<E> implements RandomAccess {
    public ObservableRandomAccessListWrapper(List<E> list) {
        super(list);
    }
}
