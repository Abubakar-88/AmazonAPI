package com.amazon.dto.state;

public class StateRequestBody {
    private String name;

    public StateRequestBody() {
    }

    public StateRequestBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}