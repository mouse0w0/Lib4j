package com.github.mouse0w0.lib4j.observable.value;

import java.util.Objects;

public class SimpleMutableBooleanValue extends ObservableValueBase<Boolean> implements ObservableBooleanValue, MutableBooleanValue {

    private boolean value;

    @Override
    public void setValue(Boolean value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableValue<Boolean> toImmutable() {
        return new ObservableBooleanValue() {
            @Override
            public boolean get() {
                return this.get();
            }

            @Override
            public Boolean getValue() {
                return this.getValue();
            }

            @Override
            public void addChangeListener(ChangeListener<Boolean> listener) {
                this.addChangeListener(listener);
            }

            @Override
            public void removeChangeListener(ChangeListener<Boolean> listener) {
                this.removeChangeListener(listener);
            }
        };
    }

    @Override
    public boolean get() {
        return value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void set(boolean value) {
        boolean oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }
}
