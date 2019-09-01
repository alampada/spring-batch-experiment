package com.ala.batchexperiment;

public class IdHolder {

    private String id;

    public IdHolder() {
    }

    public IdHolder(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdHolder{" +
                "id=" + id +
                '}';
    }
}
