package com.github.mouse0w0.lib4j.observable.value;

public interface MutableBooleanValue extends ObservableBooleanValue, MutableValue<Boolean> {

    void set(boolean value);
}
