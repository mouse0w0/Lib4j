package com.github.mouse0w0.lib4j.observable.value;

public interface MutableNumberValue<T extends Number> extends ObservableNumberValue<T>, MutableValue<T> {

    ObservableNumberValue<T> toImmutable();
}
