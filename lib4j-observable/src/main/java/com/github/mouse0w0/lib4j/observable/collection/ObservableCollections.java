package com.github.mouse0w0.lib4j.observable.collection;

import java.util.*;

public final class ObservableCollections {
    private ObservableCollections() {
    }

    public static <E> ObservableList<E> observableList(List<E> list) {
        return list instanceof RandomAccess ? new ObservableRandomAccessListWrapper<>(list) : new ObservableListWrapper<>(list);
    }

    public static <E> ObservableList<E> unmodifiableObservableList(ObservableList<E> list) {
        return new UnmodifiableObservableList<>(list);
    }

    public static <E> ObservableSet<E> observableSet(Set<E> set) {
        return new ObservableSetWrapper<>(set);
    }

    public static <E> ObservableSet<E> unmodifiableObservableSet(ObservableSet<E> set) {
        return new UnmodifiableObservableSet<>(set);
    }

    public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
        return new ObservableMapWrapper<>(map);
    }

    public static <K, V> ObservableMap<K, V> unmodifiableObservableMap(ObservableMap<K, V> map) {
        return new UnmodifiableObservableMap<>(map);
    }

    private static class UnmodifiableObservableList<E> extends AbstractList<E> implements ObservableList<E> {

        private final ObservableList<E> list;

        public UnmodifiableObservableList(ObservableList<E> list) {
            this.list = list;
        }

        @Override
        public void addChangeListener(ListChangeListener<? super E> listener) {
            list.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ListChangeListener<? super E> listener) {
            list.removeChangeListener(listener);
        }

        @Override
        public E get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }

    private static class UnmodifiableObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

        private final ObservableSet<E> set;

        public UnmodifiableObservableSet(ObservableSet<E> set) {
            this.set = set;
        }

        @Override
        public void addChangeListener(SetChangeListener<? super E> listener) {
            set.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(SetChangeListener<? super E> listener) {
            set.removeChangeListener(listener);
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private final Iterator<? extends E> i = set.iterator();

                @Override
                public boolean hasNext() {
                    return i.hasNext();
                }

                @Override
                public E next() {
                    return i.next();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableObservableMap<K, V> extends AbstractMap<K, V> implements ObservableMap<K, V> {

        private final ObservableMap<K, V> map;

        public UnmodifiableObservableMap(ObservableMap<K, V> map) {
            this.map = map;
        }

        private Set<K> keyset;
        private Collection<V> values;
        private Set<Entry<K, V>> entryset;

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        @Override
        public V get(Object key) {
            return map.get(key);
        }

        @Override
        public Set<K> keySet() {
            if (keyset == null) {
                keyset = Collections.unmodifiableSet(map.keySet());
            }
            return keyset;
        }

        @Override
        public Collection<V> values() {
            if (values == null) {
                values = Collections.unmodifiableCollection(map.values());
            }
            return values;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            if (entryset == null) {
                entryset = Collections.unmodifiableMap(map).entrySet();
            }
            return entryset;
        }

        @Override
        public void addChangeListener(MapChangeListener<? super K, ? super V> listener) {
            map.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(MapChangeListener<? super K, ? super V> listener) {
            map.removeChangeListener(listener);
        }
    }
}
