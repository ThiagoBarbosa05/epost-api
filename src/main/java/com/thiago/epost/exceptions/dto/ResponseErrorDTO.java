package com.thiago.epost.exceptions.dto;


import java.time.LocalDateTime;

public record ResponseErrorDTO(
   String message,
   String status,
   Integer statusCode,
   String field,
   LocalDateTime time
) {}
