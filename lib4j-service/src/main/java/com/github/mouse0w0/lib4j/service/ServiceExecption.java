package com.github.mouse0w0.lib4j.service;

public class ServiceExecption extends RuntimeException {
	
	public ServiceExecption() {
		super();
	}
	
	public ServiceExecption(String message) {
		super(message);
	}
	
	public ServiceExecption(Throwable throwable) {
		super(throwable);
	}
	
	public ServiceExecption(String message, Throwable throwable) {
		super(message, throwable);
	}
}
