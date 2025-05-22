package com.microservice.subscription.dtos;

import lombok.Data;

@Data
public class ErrorDto {
    private Throwable error;
    private String message;

    public ErrorDto(Throwable error) {
        this.error = error;
        this.message = error.getMessage();
    }
}
