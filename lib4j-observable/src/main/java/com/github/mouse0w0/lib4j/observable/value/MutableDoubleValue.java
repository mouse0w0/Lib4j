package com.github.mouse0w0.lib4j.observable.value;

public interface MutableDoubleValue extends MutableNumberValue<Double>, ObservableDoubleValue {

    void set(double value);

    ObservableDoubleValue toImmutable();
}
