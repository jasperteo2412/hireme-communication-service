package com.hireme.communication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessagesDTO {

    private String messageId;
    private String messageBody;
    private Date sentDateTime;
    private String readIndicator;

}
