package com.github.mouse0w0.lib4j.observable.value;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class ObservableValueBase<T> implements ObservableValue<T> {

    private final List<ChangeListener<T>> changeListeners = new LinkedList<>();

    @Override
    public void addChangeListener(ChangeListener<T> listener) {
        Objects.requireNonNull(listener);
        changeListeners.add(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener<T> listener) {
        Objects.requireNonNull(listener);
        changeListeners.remove(listener);
    }

    protected void fireValueChangeEvent(T oldValue, T newValue) {
        for (ChangeListener<T> listener : changeListeners) {
            listener.onChange(this, oldValue, newValue);
        }
    }
}
