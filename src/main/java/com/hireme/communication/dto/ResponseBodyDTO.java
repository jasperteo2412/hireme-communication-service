package com.hireme.communication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBodyDTO {

    private int status;
    private Object[] data;
    private ErrorDTO error;

}
