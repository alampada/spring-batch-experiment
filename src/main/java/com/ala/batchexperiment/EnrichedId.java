package com.ala.batchexperiment;

import java.util.Objects;

public class EnrichedId {

    private String id;

    private int value;

    public EnrichedId() {
    }

    public EnrichedId(String id, int value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrichedId that = (EnrichedId) o;
        return id == that.id &&
                value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "EnrichedId{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
