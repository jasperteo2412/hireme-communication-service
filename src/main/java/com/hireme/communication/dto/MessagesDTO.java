package com.hireme.communication.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessagesDTO {

    private int communicationId;
    private String messageBody;
    private String sentFrom;
    private String sentTo;
    private Date sentDateTime;
    private String readIndicator;

}
