package com.facturacion.util;

public record ResponseMessageDTO<T>(
    int code, String message, T content
) {

    public ResponseMessageDTO(int code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

} 
