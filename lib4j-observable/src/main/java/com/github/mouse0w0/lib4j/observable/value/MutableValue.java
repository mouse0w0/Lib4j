package com.github.mouse0w0.lib4j.observable.value;

public interface MutableValue<T> extends ObservableValue<T> {

    void setValue(T value);

    ObservableValue<T> toImmutable();
}
