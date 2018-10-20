package com.github.mouse0w0.lib4j;

public interface EventDispatcher {

    Event dispatchEvent(Event event, EventDispatchChain eventDispatchChain);
}
