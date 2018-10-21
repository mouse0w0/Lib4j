package com.github.mouse0w0.lib4j.structure.tree;

import java.util.*;

public class TireTreeImpl implements TireTree {

    private final List<String> elements = new LinkedList<String>();

    private final Node root = new Node();

    @Override
    public Collection<String> get(String prefix) {
        Node node = root;
        if (prefix == null)
            return node.get();

        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null)
                return Collections.emptyList();
        }
        return node.get();
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            private String currentElement;

            @Override
            public boolean hasNext() {
                return index < elements.size();
            }

            @Override
            public String next() {
                currentElement = elements.get(index);
                return currentElement;
            }

            @Override
            public void remove() {
                TireTreeImpl.this.remove(currentElement);
            }
        };
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elements.toArray(a);
    }

    @Override
    public boolean add(String s) {
        Objects.requireNonNull(s, "Element cannot be null.");
        int index = 0;
        for (int size = size(); index < size; index++) {
            if (s.compareTo(elements.get(index)) <= 0) {
                break;
            }
        }
        elements.add(index, s);
        add(index, s);
        return true;
    }

    private void add(int index, String s) {
        Node node = root;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            boolean flag = true;
            for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
                if(entry.getKey().equals(c)) {
                    node = entry.getValue();
                    increaseNode(node, index);
                    flag = false;
                } else {
                    increaseNodeAndChildren(entry.getValue(), index);
                }
            }

            if(flag) {
                node = node.children.put(c, new Node(index));
            }
        }
    }

    private void increaseNode(Node node, int index) {
        if (index < node.from) { // if index before the node.
            node.from++;
        }

        if (index <= node.to) { // if index before or in the node.
            node.to++;
        }
    }

    private void increaseNodeAndChildren(Node node, int index) {
        if(index > node.to) {
            return;
        }

        increaseNode(node, index);
        for(Node child : node.children.values())
            increaseNodeAndChildren(child, index);
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0, size = size(); i < size; i++) {
            String s = elements.get(i);
            if (s.equals(o)) {
                elements.remove(i);
                decreaseNodeAndChildren(root, i);
                return true;
            }
        }
        return false;
    }

    private void decreaseNode(Node node, int index) {
        if (index < node.from) { // if index before the node.
            node.from--;
        }

        if (index <= node.to) { // if index before or in the node.
            node.to--;
        }
    }

    private void decreaseNodeAndChildren(Node node, int index) {
        if(index > node.to) {
            return;
        }

        for(Map.Entry<Character, Node> entry : node.children.entrySet()) {
            Node child = entry.getValue();
            decreaseNodeAndChildren(child, index);
            if(child.to - child.from < 1) {
                node.children.remove(entry.getKey());
            }
        }
        decreaseNode(node, index);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean flag = true;
        for (String s : c) {
            if (!add(s)) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = true;
        for (Object o : c) {
            if (!remove(o)) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public void clear() {
        elements.clear();
        root.clear();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    private class Node {
        private int from, to;
        private final Map<Character, Node> children = new HashMap<>();

        public Node() {
        }

        public Node(int index) {
            this(index, index + 1);
        }

        public Node(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public void set(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public void clear() {
            children.clear();
        }

        public Collection<String> get() {
            return elements.subList(from, to);
        }
    }
}
