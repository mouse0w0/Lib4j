package com.github.mouse0w0.lib4j.observable.collection;

import java.util.List;

@FunctionalInterface
public interface ListChangeListener<E> {

    void onChanged(Change<E> change);

    abstract class Change<E> {

        private final ObservableList<E> list;

        public Change(ObservableList<E> list) {
            this.list = list;
        }

        public ObservableList<E> getList() {
            return list;
        }

        abstract public boolean wasRemoved();

        abstract public boolean wasAdded();

        abstract public List<E> getRemoved();

        abstract public List<E> getAdded();
    }

}
