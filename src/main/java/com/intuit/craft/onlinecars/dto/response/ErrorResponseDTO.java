package com.intuit.craft.onlinecars.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponseDTO extends ResponseDTO {

    private String requestId;

    private String exceptionId;

    private String exception;

    private String exceptionCode;

    private String application;

    private Map<String, Object> details;
}
