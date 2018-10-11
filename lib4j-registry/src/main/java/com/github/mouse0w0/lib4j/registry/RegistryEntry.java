package com.github.mouse0w0.lib4j.registry;

import com.google.common.reflect.TypeToken;

public interface RegistryEntry<T> {

    NamespacedKey getRegistryName();

    Class<T> getRegistryType();

    T setRegistryName(NamespacedKey location);

    default T setRegistryName(String name) {
        return setRegistryName(new NamespacedKey(name));
    }

    default T setRegistryName(String domain, String path) {
        return setRegistryName(new NamespacedKey(domain, path));
    }

    abstract class Impl<T extends RegistryEntry<T>> implements RegistryEntry<T> {

        private final TypeToken<T> token = new TypeToken<T>(getClass()) {
        };
        private NamespacedKey key;

        @Override
        public final NamespacedKey getRegistryName() {
            return key;
        }

        @SuppressWarnings("unchecked")
        @Override
        public final T setRegistryName(NamespacedKey location) {
            if (this.key != null) throw new IllegalStateException("Duplicated register");
            this.key = location;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public final Class<T> getRegistryType() {
            return (Class<T>) token.getRawType();
        }

        ;
    }
}
