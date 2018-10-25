package com.github.mouse0w0.lib4j.observable.collection;

import java.util.*;

public class ObservableListWrapper<E> extends AbstractList<E> implements ObservableList<E> {

    private final List<ListChangeListener<? super E>> listeners = new LinkedList<>();

    private final List<E> list;

    public ObservableListWrapper(List<E> list) {
        this.list = list;
    }

    @Override
    public void addChangeListener(ListChangeListener<? super E> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(ListChangeListener<? super E> listener) {
        listeners.remove(listener);
    }

    protected void fireChangeListener(ListChangeListener.Change<? super E> change) {
        for (ListChangeListener listener : listeners) {
            listener.onChanged(change);
        }
    }

    @Override
    public E set(int index, E element) {
        E removed = list.set(index, element);
        fireChangeListener(new ReplacedChange(Collections.singletonList(element), Collections.singletonList(removed)));
        return removed;
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        fireChangeListener(new AddedChange(Collections.singletonList(element)));
    }

    @Override
    public E remove(int index) {
        E element = list.remove(index);
        fireChangeListener(new RemovedChange(Collections.singletonList(element)));
        return element;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (list.addAll(c)) {
            fireChangeListener(new AddedChange(new ArrayList<>(c)));
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (list.addAll(index, c)) {
            fireChangeListener(new AddedChange(new ArrayList<>(c)));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (list.removeAll(c)) {
            fireChangeListener(new RemovedChange(new ArrayList<>((Collection<E>) c)));
            return true;
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        if (list.add(e)) {
            fireChangeListener(new AddedChange(Collections.singletonList(e)));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (list.remove(o)) {
            fireChangeListener(new AddedChange(Collections.singletonList((E) o)));
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        list.clear();
        fireChangeListener(new RemovedChange(this));
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    private class AddedChange extends ListChangeListener.Change<E> {

        private final List<E> added;

        public AddedChange(List<E> added) {
            super(ObservableListWrapper.this);
            this.added = added;
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public List<E> getRemoved() {
            return Collections.emptyList();
        }

        @Override
        public List<E> getAdded() {
            return added;
        }
    }

    private class RemovedChange extends ListChangeListener.Change<E> {

        private final List<E> removed;

        public RemovedChange(List<E> removed) {
            super(ObservableListWrapper.this);
            this.removed = removed;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public List<E> getRemoved() {
            return removed;
        }

        @Override
        public List<E> getAdded() {
            return Collections.emptyList();
        }
    }

    private class ReplacedChange extends ListChangeListener.Change<E> {

        private final List<E> added;
        private final List<E> removed;

        public ReplacedChange(List<E> added, List<E> removed) {
            super(ObservableListWrapper.this);
            this.added = added;
            this.removed = removed;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public List<E> getRemoved() {
            return removed;
        }

        @Override
        public List<E> getAdded() {
            return added;
        }
    }
}
