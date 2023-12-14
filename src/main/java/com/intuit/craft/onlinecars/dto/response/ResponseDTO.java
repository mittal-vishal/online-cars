package com.intuit.craft.onlinecars.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer statusCode = 200;
    private String statusMessage = "success";

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        Optional.ofNullable(statusCode).ifPresent(sc -> this.statusCode = sc);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        Optional.ofNullable(statusMessage).ifPresent(sm -> this.statusMessage = sm);
    }
}
