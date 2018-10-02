package com.example.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T result;
    private String description;
    private String code;

    public T getResult() {
        return this.result;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }
}
