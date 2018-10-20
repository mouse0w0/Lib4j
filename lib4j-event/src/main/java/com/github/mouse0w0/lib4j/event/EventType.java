package com.github.mouse0w0.lib4j.event;

import java.util.Map;
import java.util.Objects;

public class EventType<T extends Event> {

    public static final EventType<Event> ROOT = new EventType<>();

    private final String name;
    private final EventType<? super T> superType;

    private Map<EventType<? extends T>, Void> childType;

    private EventType() {
        this.name = "Event";
        this.superType = null;
    }

    public EventType(String name) {
        this(name, ROOT);
    }

    public EventType(String name, EventType<? super T> superType) {
        this.name = name;
        this.superType = Objects.requireNonNull(superType, "SuperType cannot be null.");
    }

    public EventType<? super T> getSuperType() {
        return superType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name != null ? name : super.toString();
    }
}
