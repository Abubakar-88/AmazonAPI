package com.amazon.dto.country;

import com.amazon.dto.state.StateResponseDTO;

import java.util.List;

public class CountryResponseDTO {
    private Integer id;
    private String name;

    private String code;

    private List<StateResponseDTO> states;

    public CountryResponseDTO() {
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

    public List<StateResponseDTO> getStates() {
        return states;
    }

    public void setStates(List<StateResponseDTO> states) {
        this.states = states;
    }
}
