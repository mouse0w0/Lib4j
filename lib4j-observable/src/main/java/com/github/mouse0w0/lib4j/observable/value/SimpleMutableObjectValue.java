package com.github.mouse0w0.lib4j.observable.value;

import java.util.Optional;

public class SimpleMutableObjectValue<T> extends ObservableValueBase<T> implements MutableValue<T> {

    private T value;
    private ImmutableObjectValue immutableValue;

    public SimpleMutableObjectValue() {
    }

    public SimpleMutableObjectValue(T value) {
        this.value = value;
    }

    public Optional<T> get() {
        return Optional.ofNullable(getValue());
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public ObservableValue<T> toImmutable() {
        if (immutableValue == null) {
            immutableValue = new ImmutableObjectValue();
        }
        return immutableValue;
    }

    private class ImmutableObjectValue implements ObservableValue<T> {
        @Override
        public T getValue() {
            return SimpleMutableObjectValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super T> listener) {
            SimpleMutableObjectValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super T> listener) {
            SimpleMutableObjectValue.this.removeChangeListener(listener);
        }
    }
}
