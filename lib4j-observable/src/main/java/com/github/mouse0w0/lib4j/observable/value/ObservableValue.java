package com.github.mouse0w0.lib4j.observable.value;

import com.github.mouse0w0.lib4j.observable.Observable;

public interface ObservableValue<T> extends Observable {

    T getValue();

    void addChangeListener(ChangeListener<? super T> listener);

    void removeChangeListener(ChangeListener<? super T> listener);
}
