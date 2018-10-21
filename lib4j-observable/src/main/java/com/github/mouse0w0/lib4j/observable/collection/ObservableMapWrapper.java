package com.github.mouse0w0.lib4j.observable.collection;

import java.util.*;

public class ObservableMapWrapper<K, V> extends AbstractMap<K, V> implements ObservableMap<K, V> {

    private ObservableEntrySet entrySet;
    private ObservableKeySet keySet;
    private ObservableValues values;

    private final List<MapChangeListener<? super K, ? super V>> changeListeners = new LinkedList<>();

    private final Map<K, V> map;

    public ObservableMapWrapper(Map<K, V> map) {
        this.map = map;
    }

    private class SimpleChange extends MapChangeListener.Change<K, V> {

        private final K key;
        private final V removed;
        private final V added;
        private final boolean wasAdded;
        private final boolean wasRemoved;

        public SimpleChange(K key, V removed, V added, boolean wasAdded, boolean wasRemoved) {
            super(ObservableMapWrapper.this);
            this.key = key;
            this.removed = removed;
            this.added = added;
            this.wasAdded = wasAdded;
            this.wasRemoved = wasRemoved;
        }

        @Override
        public boolean wasAdded() {
            return wasAdded;
        }

        @Override
        public boolean wasRemoved() {
            return wasRemoved;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getAddedValue() {
            return added;
        }

        @Override
        public V getRemovedValue() {
            return removed;
        }
    }

    protected void callObservers(MapChangeListener.Change<K, V> change) {
        for (MapChangeListener listener : changeListeners) {
            listener.onChanged(change);
        }
    }

    @Override
    public void addChangeListener(MapChangeListener<? super K, ? super V> observer) {
        Objects.requireNonNull(observer);
        changeListeners.add(observer);
    }

    @Override
    public void removeChangeListener(MapChangeListener<? super K, ? super V> observer) {
        changeListeners.remove(observer);
    }

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
    public V put(K key, V value) {
        V ret;
        if (map.containsKey(key)) {
            ret = map.put(key, value);
            if (ret == null && value != null || ret != null && !ret.equals(value)) {
                callObservers(new SimpleChange(key, ret, value, true, true));
            }
        } else {
            ret = map.put(key, value);
            callObservers(new SimpleChange(key, ret, value, true, false));
        }
        return ret;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        if (!map.containsKey(key)) {
            return null;
        }
        V ret = map.remove(key);
        callObservers(new SimpleChange((K) key, ret, null, false, true));
        return ret;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        for (Iterator<Entry<K, V>> i = map.entrySet().iterator(); i.hasNext(); ) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V val = e.getValue();
            i.remove();
            callObservers(new SimpleChange(key, val, null, false, true));
        }
    }

    @Override
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new ObservableKeySet();
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        if (values == null) {
            values = new ObservableValues();
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new ObservableEntrySet();
        }
        return entrySet;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return map.equals(obj);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    private class ObservableKeySet implements Set<K> {

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return map.keySet().contains(o);
        }

        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {

                private Iterator<Entry<K, V>> entryIt = map.entrySet().iterator();
                private K lastKey;
                private V lastValue;

                @Override
                public boolean hasNext() {
                    return entryIt.hasNext();
                }

                @Override
                public K next() {
                    Entry<K, V> last = entryIt.next();
                    lastKey = last.getKey();
                    lastValue = last.getValue();
                    return last.getKey();
                }

                @Override
                public void remove() {
                    entryIt.remove();
                    callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
                }

            };
        }

        @Override
        public Object[] toArray() {
            return map.keySet().toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return map.keySet().toArray(a);
        }

        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean remove(Object o) {
            return ObservableMapWrapper.this.remove(o) != null;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return map.keySet().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return removeRetain(c, false);
        }

        private boolean removeRetain(Collection<?> c, boolean remove) {
            boolean removed = false;
            for (Iterator<Entry<K, V>> i = map.entrySet().iterator(); i.hasNext(); ) {
                Entry<K, V> e = i.next();
                if (remove == c.contains(e.getKey())) {
                    removed = true;
                    K key = e.getKey();
                    V value = e.getValue();
                    i.remove();
                    callObservers(new SimpleChange(key, value, null, false, true));
                }
            }
            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return removeRetain(c, true);
        }

        @Override
        public void clear() {
            ObservableMapWrapper.this.clear();
        }

        @Override
        public String toString() {
            return map.keySet().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return map.keySet().equals(obj);
        }

        @Override
        public int hashCode() {
            return map.keySet().hashCode();
        }

    }

    private class ObservableValues implements Collection<V> {

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return map.values().contains(o);
        }

        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {

                private Iterator<Entry<K, V>> entryIt = map.entrySet().iterator();
                private K lastKey;
                private V lastValue;

                @Override
                public boolean hasNext() {
                    return entryIt.hasNext();
                }

                @Override
                public V next() {
                    Entry<K, V> last = entryIt.next();
                    lastKey = last.getKey();
                    lastValue = last.getValue();
                    return lastValue;
                }

                @Override
                public void remove() {
                    entryIt.remove();
                    callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
                }

            };
        }

        @Override
        public Object[] toArray() {
            return map.values().toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return map.values().toArray(a);
        }

        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean remove(Object o) {
            for (Iterator<V> i = iterator(); i.hasNext(); ) {
                if (i.next().equals(o)) {
                    i.remove();
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return map.values().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return removeRetain(c, true);
        }

        private boolean removeRetain(Collection<?> c, boolean remove) {
            boolean removed = false;
            for (Iterator<Entry<K, V>> i = map.entrySet().iterator(); i.hasNext(); ) {
                Entry<K, V> e = i.next();
                if (remove == c.contains(e.getValue())) {
                    removed = true;
                    K key = e.getKey();
                    V value = e.getValue();
                    i.remove();
                    callObservers(new SimpleChange(key, value, null, false, true));
                }
            }
            return removed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return removeRetain(c, false);
        }

        @Override
        public void clear() {
            ObservableMapWrapper.this.clear();
        }

        @Override
        public String toString() {
            return map.values().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return map.values().equals(obj);
        }

        @Override
        public int hashCode() {
            return map.values().hashCode();
        }


    }

    private class ObservableEntry implements Entry<K, V> {

        private final Entry<K, V> backingEntry;

        public ObservableEntry(Entry<K, V> backingEntry) {
            this.backingEntry = backingEntry;
        }

        @Override
        public K getKey() {
            return backingEntry.getKey();
        }

        @Override
        public V getValue() {
            return backingEntry.getValue();
        }

        @Override
        public V setValue(V value) {
            V oldValue = backingEntry.setValue(value);
            callObservers(new SimpleChange(getKey(), oldValue, value, true, true));
            return oldValue;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public final int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode())
                    ^ (getValue() == null ? 0 : getValue().hashCode());
        }

        @Override
        public final String toString() {
            return getKey() + "=" + getValue();
        }

    }

    private class ObservableEntrySet implements Set<Entry<K, V>> {

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return map.entrySet().contains(o);
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new Iterator<Entry<K, V>>() {

                private Iterator<Entry<K, V>> backingIt = map.entrySet().iterator();
                private K lastKey;
                private V lastValue;

                @Override
                public boolean hasNext() {
                    return backingIt.hasNext();
                }

                @Override
                public Entry<K, V> next() {
                    Entry<K, V> last = backingIt.next();
                    lastKey = last.getKey();
                    lastValue = last.getValue();
                    return new ObservableEntry(last);
                }

                @Override
                public void remove() {
                    backingIt.remove();
                    callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
                }
            };
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object[] toArray() {
            Object[] array = map.entrySet().toArray();
            for (int i = 0; i < array.length; ++i) {
                array[i] = new ObservableEntry((Entry<K, V>) array[i]);
            }
            return array;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            T[] array = map.entrySet().toArray(a);
            for (int i = 0; i < array.length; ++i) {
                array[i] = (T) new ObservableEntry((Entry<K, V>) array[i]);
            }
            return array;
        }

        @Override
        public boolean add(Entry<K, V> e) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object o) {
            boolean ret = map.entrySet().remove(o);
            if (ret) {
                Entry<K, V> entry = (Entry<K, V>) o;
                callObservers(new SimpleChange(entry.getKey(), entry.getValue(), null, false, true));
            }
            return ret;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return map.entrySet().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends Entry<K, V>> c) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return removeRetain(c, false);
        }

        private boolean removeRetain(Collection<?> c, boolean remove) {
            boolean removed = false;
            for (Iterator<Entry<K, V>> i = map.entrySet().iterator(); i.hasNext(); ) {
                Entry<K, V> e = i.next();
                if (remove == c.contains(e)) {
                    removed = true;
                    K key = e.getKey();
                    V value = e.getValue();
                    i.remove();
                    callObservers(new SimpleChange(key, value, null, false, true));
                }
            }
            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return removeRetain(c, true);
        }

        @Override
        public void clear() {
            ObservableMapWrapper.this.clear();
        }

        @Override
        public String toString() {
            return map.entrySet().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return map.entrySet().equals(obj);
        }

        @Override
        public int hashCode() {
            return map.entrySet().hashCode();
        }

    }
}
