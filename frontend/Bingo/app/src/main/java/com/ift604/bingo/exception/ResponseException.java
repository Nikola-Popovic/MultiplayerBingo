package com.ift604.bingo.exception;

public class ResponseException extends  Exception {
    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(int responseCode, String errorDetail) {
        super(String.format("%d : %s", responseCode, errorDetail));
    }
}
