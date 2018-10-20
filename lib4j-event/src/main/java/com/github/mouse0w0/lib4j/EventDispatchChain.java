package com.github.mouse0w0.lib4j;

public interface EventDispatchChain {

    EventDispatchChain append(EventDispatcher eventDispatcher);

    EventDispatchChain prepend(EventDispatcher eventDispatcher);

    Event dispatchEvent(Event event);
}
