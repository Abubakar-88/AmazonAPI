package com.amazon.dto.country;

import com.amazon.dto.state.StateRequestBody;

public class CountryRequestBody {
    private Integer id;
    private StateRequestBody state;

    public CountryRequestBody() {
    }

    public CountryRequestBody(Integer id, StateRequestBody state) {
        this.id = id;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StateRequestBody getState() {
        return state;
    }

    public void setState(StateRequestBody state) {
        this.state = state;
    }
}