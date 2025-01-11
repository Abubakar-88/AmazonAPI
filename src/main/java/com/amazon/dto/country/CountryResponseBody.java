package com.amazon.dto.country;


import com.amazon.dto.state.StateResponseBody;

public class CountryResponseBody {

    private Integer id;
    private String name;
    private String code;
    private StateResponseBody state; // Only include the selected state

    public CountryResponseBody() {
    }

    public CountryResponseBody(Integer id, String name, String code, StateResponseBody state) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StateResponseBody getState() {
        return state;
    }

    public void setState(StateResponseBody state) {
        this.state = state;
    }
}