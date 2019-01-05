package com.github.mouse0w0.lib4j.observable.value;

public interface MutableIntValue extends MutableNumberValue<Integer>, ObservableIntValue {

    void set(int value);

    ObservableIntValue toImmutable();
}
