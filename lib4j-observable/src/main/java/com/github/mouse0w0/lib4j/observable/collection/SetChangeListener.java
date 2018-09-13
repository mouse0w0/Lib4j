package com.github.mouse0w0.lib4j.observable.collection;

@FunctionalInterface
public interface SetChangeListener<E> {

    void onChanged(Change<E> change);

    public static abstract class Change<E> {

        private final ObservableSet<E> set;

        public Change(ObservableSet<E> set) {
            this.set = set;
        }

        public ObservableSet<E> getSet() {
            return set;
        }

        public abstract boolean wasAdded();

        public abstract boolean wasRemoved();

        public abstract E getAddedElement();

        public abstract E getRemovedElement();
    }
}
