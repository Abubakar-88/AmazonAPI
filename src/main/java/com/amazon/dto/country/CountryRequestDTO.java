package com.amazon.dto.country;

import com.amazon.dto.state.StateRequestDTO;

import java.util.List;

public class CountryRequestDTO {

    private String name;

    private String code;

    private List<StateRequestDTO> states;

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

    public List<StateRequestDTO> getStates() {
        return states;
    }

    public void setStates(List<StateRequestDTO> states) {
        this.states = states;
    }
}
