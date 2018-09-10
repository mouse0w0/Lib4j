package com.github.mouse0w0.lib4j.observable.value;

import java.util.Objects;

public class SimpleMutableFloatValue extends ObservableValueBase<Float> implements MutableFloatValue {

    private float value;
    private ImmutableFloatValue immutableFloatValue;

    @Override
    public void set(float value) {
        float oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public void setValue(Float value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableFloatValue toImmutable() {
        if (immutableFloatValue == null)
            immutableFloatValue = new ImmutableFloatValue();
        return immutableFloatValue;
    }

    @Override
    public float get() {
        return value;
    }

    @Override
    public byte getByte() {
        return (byte) value;
    }

    @Override
    public short getShort() {
        return (short) value;
    }

    @Override
    public int getInt() {
        return (int) value;
    }

    @Override
    public long getLong() {
        return (long) value;
    }

    @Override
    public float getFloat() {
        return value;
    }

    @Override
    public double getDouble() {
        return value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    private class ImmutableFloatValue implements ObservableFloatValue {
        @Override
        public float get() {
            return SimpleMutableFloatValue.this.get();
        }

        @Override
        public byte getByte() {
            return SimpleMutableFloatValue.this.getByte();
        }

        @Override
        public short getShort() {
            return SimpleMutableFloatValue.this.getShort();
        }

        @Override
        public int getInt() {
            return SimpleMutableFloatValue.this.getInt();
        }

        @Override
        public long getLong() {
            return SimpleMutableFloatValue.this.getLong();
        }

        @Override
        public float getFloat() {
            return SimpleMutableFloatValue.this.getFloat();
        }

        @Override
        public double getDouble() {
            return SimpleMutableFloatValue.this.getDouble();
        }

        @Override
        public Float getValue() {
            return SimpleMutableFloatValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super Float> listener) {
            SimpleMutableFloatValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super Float> listener) {
            SimpleMutableFloatValue.this.removeChangeListener(listener);
        }
    }
}
