package com.hireme.communication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBodyDTO {

    private int status;
    private Object[] data;
    private ErrorDTO error;

}
