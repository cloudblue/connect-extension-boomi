package com.cloudblue.connect.browser;

import com.boomi.connector.api.ObjectType;

import java.util.HashMap;
import java.util.Map;

public class ResourceInfo {

    private String id;
    private String name;
    private Map<String, OperationSchemaInfo> operationSchemas = new HashMap<>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, OperationSchemaInfo> getOperationSchemas() {
        return operationSchemas;
    }

    public ResourceInfo id(String id) {
        this.id = id;
        return this;
    }

    public ResourceInfo name(String name) {
        this.name = name;
        return this;
    }

    public ResourceInfo operationSchema(String operationName, OperationSchemaInfo schemaInfo) {
        this.operationSchemas.put(operationName, schemaInfo);
        return this;
    }

    public ObjectType objectType() {
        ObjectType type = new ObjectType();
        type.setId(this.id);
        type.setLabel(this.name);

        return type;
    }
}
