package com.github.mouse0w0.lib4j.observable.value;

public interface ObservableValue<T> {

    T getValue();

    void addChangeListener(ChangeListener<T> listener);

    void removeChangeListener(ChangeListener<T> listener);
}
