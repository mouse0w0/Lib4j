package com.github.mouse0w0.lib4j.observable.value;

import com.github.mouse0w0.lib4j.observable.Observable;

public interface ObservableValue<T> extends Observable {

    T getValue();

    void addChangeListener(ValueChangeListener<? super T> listener);

    void removeChangeListener(ValueChangeListener<? super T> listener);
}
