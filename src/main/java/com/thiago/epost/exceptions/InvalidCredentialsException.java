package com.thiago.epost.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Email/senha estão incorretos.");
    }
}
