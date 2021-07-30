package com.cloudblue.connect.browser;

public class OperationSchemaInfo {
    private String input;
    private String output;

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public OperationSchemaInfo input(String input) {
        this.input = input;
        return this;
    }

    public OperationSchemaInfo output(String output) {
        this.output = output;
        return this;
    }
}
