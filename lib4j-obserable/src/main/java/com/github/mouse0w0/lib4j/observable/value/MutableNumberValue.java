package com.github.mouse0w0.lib4j.observable.value;

public interface MutableNumberValue extends ObservableNumberValue, MutableValue<Number> {

    void setValue(Number value);
}
