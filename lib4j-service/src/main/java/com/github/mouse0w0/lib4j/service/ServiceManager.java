package com.github.mouse0w0.lib4j.service;

import java.util.Collection;
import java.util.Optional;

public interface ServiceManager {

	<T extends Service> Optional<T> getService(Class<T> clazz);

	<T extends Service> void register(Class<T> clazz, T service);

	<T extends Service> void register(ServiceProvider<T> provider);

	<T extends Service> void unregister(Class<T> clazz);
	
	Collection<Class<?>> getProvidedServices();

	<T extends Service> boolean isProvidedFor(Class<T> clazz);

}
