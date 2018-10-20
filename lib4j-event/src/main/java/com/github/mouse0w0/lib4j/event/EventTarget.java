package com.github.mouse0w0.lib4j.event;

public interface EventTarget {

    EventDispatchChain buildEventDispatchChain(EventDispatchChain tail);
}
