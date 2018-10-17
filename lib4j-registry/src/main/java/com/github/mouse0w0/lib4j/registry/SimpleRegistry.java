package com.github.mouse0w0.lib4j.registry;

public class SimpleRegistry<T extends RegistryEntry<T>> extends RegistryBase<T> {

    private int nextId = -1;

    @Override
    protected int nextId(T obj) {
        nextId++;
        return nextId;
    }
}
