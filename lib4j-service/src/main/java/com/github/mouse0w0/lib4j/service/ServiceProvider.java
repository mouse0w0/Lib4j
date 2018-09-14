package com.github.mouse0w0.lib4j.service;

public interface ServiceProvider<T extends Service> {

	Class<T> getServiceClass();

	T getService();
	
	boolean isReady();
}
