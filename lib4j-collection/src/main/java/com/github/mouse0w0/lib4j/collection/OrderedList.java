package com.github.mouse0w0.lib4j.collection;

import java.util.*;

public class OrderedList<E> extends AbstractList<E> {

    public static <E> OrderedList<E> create(Comparator<E> comparator) {
        return new OrderedList<>(new LinkedList<>(), comparator);
    }

    public static <E extends Comparable<E>> OrderedList<E> create() {
        return create(Comparable::compareTo);
    }

    public static <E> OrderedList<E> wrap(List<E> list, Comparator<E> comparator) {
        for (int i = 0, size = list.size(); i < size; i++) {
            E element = list.get(i);
            if (element == null)
                list.remove(i);
        }
        return new OrderedList<>(list, comparator);
    }

    public static <E extends Comparable<E>> OrderedList<E> wrap(List<E> list) {
        return wrap(list, Comparable::compareTo);
    }

    public static <E> OrderedList<E> from(Comparator<E> comparator, E... elements) {
        return new OrderedList<>(Arrays.asList(elements), comparator);
    }

    public static <E extends Comparable<E>> OrderedList<E> from(E... elements) {
        return from(Comparable::compareTo, elements);
    }

    private final List<E> list;
    private final Comparator<E> comparator;

    protected OrderedList(List<E> list, Comparator<E> comparator) {
        this.list = list;
        this.comparator = comparator;
    }

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e, "Element cannot be null.");
        int index = 0;
        for (int size = size(); index < size; index++) {
            if (comparator.compare(e, get(index)) <= 0) {
                break;
            }
        }
        super.add(index, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(int index) {
        return list.get(index);
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
