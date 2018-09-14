package com.github.mouse0w0.lib4j.service;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class SimpleServiceManager implements ServiceManager {

    private final Map<Class<?>, ServiceProvider<?>> services = Collections.synchronizedMap(new HashMap<>());

    public SimpleServiceManager() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Service> Optional<T> getService(Class<T> clazz) {
        ServiceProvider<?> serviceProvider = services.get(requireNonNull(clazz, "Class cannot be null"));
        return Optional.ofNullable(
                (T) (serviceProvider != null && serviceProvider.isReady() ? serviceProvider.getService() : null));
    }

    @Override
    public <T extends Service> void register(Class<T> clazz, T service) {
        requireNonNull(clazz, "Class cannot be null");
        requireNonNull(service, "Service cannot be null");
        services.put(clazz, new ServiceProvider<T>() {

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public Class<T> getServiceClass() {
                return clazz;
            }

            @Override
            public T getService() {
                return service;
            }
        });
    }

    @Override
    public <T extends Service> void register(ServiceProvider<T> provider) {
        requireNonNull(provider, "Provider cannot be null");
        services.put(provider.getServiceClass(), provider);
    }

    @Override
    public <T extends Service> void unregister(Class<T> clazz) {
        services.remove(requireNonNull(clazz, "Class cannot be null"));
    }

    @Override
    public <T extends Service> boolean isProvidedFor(Class<T> clazz) {
        return services.containsKey(clazz);
    }

    @Override
    public Collection<Class<?>> getProvidedServices() {
        return services.keySet();
    }

}
