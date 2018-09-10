package com.github.mouse0w0.lib4j.observable.value;

import java.util.Objects;

public class SimpleMutableDoubleValue extends ObservableValueBase<Double> implements MutableDoubleValue {

    private double value;
    private ImmutableDoubleValue immutableDoubleValue;

    @Override
    public void set(double value) {
        double oldValue = this.value;
        this.value = value;
        fireValueChangeEvent(oldValue, value);
    }

    @Override
    public void setValue(Double value) {
        Objects.requireNonNull(value);
        set(value);
    }

    @Override
    public ObservableDoubleValue toImmutable() {
        if (immutableDoubleValue == null)
            immutableDoubleValue = new ImmutableDoubleValue();
        return immutableDoubleValue;
    }

    @Override
    public double get() {
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
        return (float) value;
    }

    @Override
    public double getDouble() {
        return value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    private class ImmutableDoubleValue implements ObservableDoubleValue {
        @Override
        public double get() {
            return SimpleMutableDoubleValue.this.get();
        }

        @Override
        public byte getByte() {
            return SimpleMutableDoubleValue.this.getByte();
        }

        @Override
        public short getShort() {
            return SimpleMutableDoubleValue.this.getShort();
        }

        @Override
        public int getInt() {
            return SimpleMutableDoubleValue.this.getInt();
        }

        @Override
        public long getLong() {
            return SimpleMutableDoubleValue.this.getLong();
        }

        @Override
        public float getFloat() {
            return SimpleMutableDoubleValue.this.getFloat();
        }

        @Override
        public double getDouble() {
            return SimpleMutableDoubleValue.this.getDouble();
        }

        @Override
        public Double getValue() {
            return SimpleMutableDoubleValue.this.getValue();
        }

        @Override
        public void addChangeListener(ValueChangeListener<? super Double> listener) {
            SimpleMutableDoubleValue.this.addChangeListener(listener);
        }

        @Override
        public void removeChangeListener(ValueChangeListener<? super Double> listener) {
            SimpleMutableDoubleValue.this.removeChangeListener(listener);
        }
    }
}

