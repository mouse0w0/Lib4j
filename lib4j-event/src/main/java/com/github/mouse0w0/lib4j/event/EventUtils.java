package com.github.mouse0w0.lib4j.event;

public class EventUtils {

    private static final ThreadLocal<EventDispatchChainImpl> eventDispatchChain = ThreadLocal.withInitial(EventDispatchChainImpl::new);

    public static Event fireEvent(Event event, EventTarget eventTarget) {
        EventDispatchChainImpl eventDispatchChain = EventUtils.eventDispatchChain.get();
        eventDispatchChain.reset();
        return eventTarget.buildEventDispatchChain(eventDispatchChain).dispatchEvent(event);
    }

    public static Event fireEvent(Event event, EventTarget... eventTargets) {
        EventDispatchChainImpl eventDispatchChain = EventUtils.eventDispatchChain.get();
        eventDispatchChain.reset();
        for(EventTarget eventTarget : eventTargets)
            eventTarget.buildEventDispatchChain(eventDispatchChain);
        return eventDispatchChain.dispatchEvent(event);
    }
}
