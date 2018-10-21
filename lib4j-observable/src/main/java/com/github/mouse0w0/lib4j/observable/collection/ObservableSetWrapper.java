package com.github.mouse0w0.lib4j.observable.collection;

import java.util.*;

public class ObservableSetWrapper<E> extends AbstractSet<E> implements ObservableSet<E> {

    private final List<SetChangeListener<? super E>> changeListeners = new LinkedList<>();
    private final Set<E> set;

    public ObservableSetWrapper(Set<E> set) {
        this.set = set;
    }

    @Override
    public void addChangeListener(SetChangeListener<? super E> listener) {
        Objects.requireNonNull(listener);
        changeListeners.add(listener);
    }

    @Override
    public void removeChangeListener(SetChangeListener<? super E> listener) {
        changeListeners.remove(listener);
    }

    protected void fireChangeListener(SetChangeListener.Change<? super E> change) {
        for (SetChangeListener listener : changeListeners) {
            listener.onChanged(change);
        }
    }

    @Override
    public boolean add(E e) {
        if (super.add(e)) {
            fireChangeListener(new AddedChange(e));
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            Iterator<E> iterator = set.iterator();
            E lastElement;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                lastElement = iterator.next();
                return lastElement;
            }

            @Override
            public void remove() {
                iterator.remove();
                fireChangeListener(new RemovedChange(lastElement));
            }
        };
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public void clear() {
        set.clear();
    }

    private class AddedChange extends SetChangeListener.Change<E> {

        private final E element;

        public AddedChange(E element) {
            super(ObservableSetWrapper.this);
            this.element = element;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public E getAddedElement() {
            return element;
        }

        @Override
        public E getRemovedElement() {
            return null;
        }
    }

    private class RemovedChange extends SetChangeListener.Change<E> {

        private final E element;

        private RemovedChange(E element) {
            super(ObservableSetWrapper.this);
            this.element = element;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public E getAddedElement() {
            return null;
        }

        @Override
        public E getRemovedElement() {
            return element;
        }
    }
}
