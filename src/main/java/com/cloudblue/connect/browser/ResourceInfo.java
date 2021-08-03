package com.cloudblue.connect.browser;

import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.OperationType;

import java.util.EnumMap;
import java.util.Map;

public class ResourceInfo {

    private String id;
    private String name;
    private EnumMap<OperationType, OperationSchemaInfo> operationSchemas = new EnumMap<>(OperationType.class);

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<OperationType, OperationSchemaInfo> getOperationSchemas() {
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

    public ResourceInfo operationSchema(OperationType operationType, OperationSchemaInfo schemaInfo) {
        this.operationSchemas.put(operationType, schemaInfo);
        return this;
    }

    public ObjectType objectType() {
        ObjectType type = new ObjectType();
        type.setId(this.id);
        type.setLabel(this.name);

        return type;
    }
}
