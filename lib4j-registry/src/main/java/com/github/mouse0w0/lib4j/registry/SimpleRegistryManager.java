package com.github.mouse0w0.lib4j.registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleRegistryManager implements RegistryManager {

    private final Map<Class<?>, Registry<?>> registries = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T extends RegistryEntry<T>> Registry<T> getRegistry(@Nonnull Class<T> type) {
        Registry<T> registry = (Registry<T>)registries.get(type);
        if(registry == null) {
            registry = new SimpleRegistry<>();
            addRegistry(type, registry);
        }
        return registry;
    }

    @Override
    public <T extends RegistryEntry<T>> boolean hasRegistry(@Nonnull Class<T> type) {
        return registries.containsKey(type);
    }

    @Override
    public <T extends RegistryEntry<T>> void addRegistry(@Nonnull Class<T> type, @Nonnull Registry<T> registry) {
        registries.put(registry.getRegistryEntryType(), registry);
    }

    @Override
    public Collection<Map.Entry<Class<?>, Registry<?>>> getEntries() {
        return registries.entrySet();
    }

    @Override
    public <T extends RegistryEntry<T>> void register(@Nonnull T obj) {
        getRegistry(obj.getRegistryType()).register(Objects.requireNonNull(obj));
    }
}