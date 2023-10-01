package com.hireme.communication.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessagesDTO {

    private int messageId;
    private String messageBody;
    private Date sentDateTime;
    private String readIndicator;

}
