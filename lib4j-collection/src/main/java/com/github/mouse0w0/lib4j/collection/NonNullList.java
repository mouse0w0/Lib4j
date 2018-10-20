package com.github.mouse0w0.lib4j.collection;

import java.util.*;

public class NonNullList<E> extends AbstractList<E> {

    public static <E> NonNullList<E> create() {
        return new NonNullList<>(new ArrayList<>());
    }

    public static <E> NonNullList<E> wrap(List<E> list) {
        for (int i = 0, size = list.size(); i < size; i++) {
            E element = list.get(i);
            if (element == null)
                list.remove(i);
        }
        return new NonNullList<>(list);
    }

    public static <E> NonNullList<E> from(E... elements) {
        return new NonNullList<>(Arrays.asList(elements));
    }

    private final List<E> list;

    protected NonNullList(List<E> list) {
        this.list = list;
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        Objects.requireNonNull(element, "Element cannot be null.");
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        Objects.requireNonNull(element, "Element cannot be null.");
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int size() {
        return list.size();
    }
}
