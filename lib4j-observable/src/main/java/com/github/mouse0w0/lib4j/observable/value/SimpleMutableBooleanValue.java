package com.github.mouse0w0.lib4j.observable.value;

import java.util.Objects;

public class SimpleMutableBooleanValue extends ObservableValueBase<Boolean> implements MutableBooleanValue {

    private boolean value;
    private ImmutableBooleanValue immutableBooleanValue;

    @Override
    public void setValue(Boolean value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableBooleanValue toImmutable() {
        if(immutableBooleanValue == null)
            immutableBooleanValue = new ImmutableBooleanValue();
        return immutableBooleanValue;
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

    private class ImmutableBooleanValue implements ObservableBooleanValue {
        @Override
        public boolean get() {
            return SimpleMutableBooleanValue.this.get();
        }

        @Override
        public Boolean getValue() {
            return SimpleMutableBooleanValue.this.getValue();
        }

        @Override
        public void addChangeListener(ChangeListener<? super Boolean> listener) {
            SimpleMutableBooleanValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ChangeListener<? super Boolean> listener) {
            SimpleMutableBooleanValue.this.removeChangeListener(listener);
        }
    }
}
