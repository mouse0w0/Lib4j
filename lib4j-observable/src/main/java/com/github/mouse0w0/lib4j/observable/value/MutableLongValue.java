package com.github.mouse0w0.lib4j.observable.value;

public interface MutableLongValue extends MutableNumberValue<Long>, ObservableLongValue {

    void set(long value);
}
