package com.github.mouse0w0.lib4j.observable.value;

@FunctionalInterface
public interface ChangeListener<T> {

    void onChange(ObservableValue<T> observable, T oldValue, T newValue);
}
