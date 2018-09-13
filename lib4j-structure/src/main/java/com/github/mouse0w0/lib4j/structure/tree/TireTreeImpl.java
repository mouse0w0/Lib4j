package com.github.mouse0w0.lib4j.structure.tree;

import java.util.*;

public class TireTreeImpl implements TireTree {

    private final List<String> elements = new LinkedList<String>() {

        @Override
        public boolean add(String s) {
            return super.add(s);
        }

        @Override
        public String set(int index, String element) {
            throw new UnsupportedOperationException();
        }
    };

    private final Node root = new Node();

    @Override
    public Collection<String> get(String prefix) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<String> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(String s) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    private class Node {
        private int from, to;
        private final Map<Character, Node> childrens = new HashMap<>();

        public Node() {}

        public Node(int index) {
            this(index, index + 1);
        }

        public Node(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public Collection<String> get() {
            return elements.subList(from, to);
        }
    }
}
