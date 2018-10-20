package com.github.mouse0w0.lib4j;

public interface EventHandler<T extends Event> {

    void onEvent(T event);
}
