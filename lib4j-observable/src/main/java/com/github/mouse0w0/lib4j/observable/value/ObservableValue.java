package com.github.mouse0w0.lib4j.observable.value;

import com.github.mouse0w0.lib4j.observable.Observable;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ObservableValue<T> extends Observable {

    T getValue();

    void addChangeListener(ValueChangeListener<? super T> listener);

    void removeChangeListener(ValueChangeListener<? super T> listener);

    default Optional<T> optional() {
        return Optional.ofNullable(getValue());
    }

    default boolean isPresent() {
        return getValue() != null;
    }

    default boolean isEmpty() {
        return getValue() == null;
    }

    default void ifPresent(Consumer<? super T> action) {
        T value = getValue();
        if (value != null) {
            action.accept(value);
        }
    }

    default void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        T value = getValue();
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    default Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(getValue());
        }
    }

    default T orElse(T other) {
        T value = getValue();
        return value != null ? value : other;
    }

    default T orElseGet(Supplier<? extends T> supplier) {
        T value = getValue();
        return value != null ? value : supplier.get();
    }

    default T orElseThrow() {
        T value = getValue();
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    default <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        T value = getValue();
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
