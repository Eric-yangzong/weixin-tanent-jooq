package com.bdhanbang.weixin.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.util.UUID;

public class MyData implements Serializable {

    private static final long serialVersionUID = 1815164277;

    private UUID     id;
    private String   dataType;
    private JsonNode jsonb;

    public MyData() {}

    public MyData(MyData value) {
        this.id = value.id;
        this.dataType = value.dataType;
        this.jsonb = value.jsonb;
    }

    public MyData(
        UUID     id,
        String   dataType,
        JsonNode jsonb
    ) {
        this.id = id;
        this.dataType = dataType;
        this.jsonb = jsonb;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public JsonNode getJsonb() {
        return this.jsonb;
    }

    public void setJsonb(JsonNode jsonb) {
        this.jsonb = jsonb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MyData (");

        sb.append(id);
        sb.append(", ").append(dataType);
        sb.append(", ").append(jsonb);

        sb.append(")");
        return sb.toString();
    }
}

