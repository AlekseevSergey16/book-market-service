package com.salekseev.booksmarket.dto;

import lombok.Data;

@Data
public class ErrorInfo {

    String message;

    public ErrorInfo(String message) {
        this.message = message;
    }
}
