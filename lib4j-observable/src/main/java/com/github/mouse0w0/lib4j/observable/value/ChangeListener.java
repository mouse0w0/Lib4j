package com.github.mouse0w0.lib4j.observable.value;

@FunctionalInterface
public interface ChangeListener<T> {

    void onChanged(ObservableValue<T> observable, T oldValue, T newValue);
}
