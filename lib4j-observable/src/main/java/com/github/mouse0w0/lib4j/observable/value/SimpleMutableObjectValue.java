package com.github.mouse0w0.lib4j.observable.value;

public class SimpleMutableObjectValue<T> extends ObservableValueBase<T> implements MutableValue<T> {
    
    private T value;

    public SimpleMutableObjectValue() {
    }

    public SimpleMutableObjectValue(T value) {
        this.value = value;
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
        return new ObservableValue<T>() {

            @Override
            public T getValue() {
                return this.getValue();
            }

            @Override
            public void addChangeListener(ChangeListener<T> listener) {
                this.addChangeListener(listener);
            }

            @Override
            public void removeChangeListener(ChangeListener<T> listener) {
                this.removeChangeListener(listener);
            }
        };
    }
}
