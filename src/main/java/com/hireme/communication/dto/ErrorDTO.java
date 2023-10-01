package com.hireme.communication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private String error;
    private String errorCode;
    private String exception;
    private String message;

}
