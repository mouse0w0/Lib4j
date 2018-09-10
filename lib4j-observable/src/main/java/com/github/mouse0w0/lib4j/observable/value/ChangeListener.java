package com.github.mouse0w0.lib4j.observable.value;

import com.github.mouse0w0.lib4j.observable.Observable;

@FunctionalInterface
public interface ChangeListener<T> {

    void onChanged(Observable observable, T oldValue, T newValue);
}
