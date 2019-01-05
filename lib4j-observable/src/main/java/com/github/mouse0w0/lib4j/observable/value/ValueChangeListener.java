package com.github.mouse0w0.lib4j.observable.value;

@FunctionalInterface
public interface ValueChangeListener<T> {

    void onChanged(ObservableValue<? extends T> observable, T oldValue, T newValue);
}
