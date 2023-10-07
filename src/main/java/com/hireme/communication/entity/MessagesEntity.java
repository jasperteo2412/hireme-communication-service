package com.hireme.communication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name =  "communication")
public class MessagesEntity {

    @Id
    @Column(name = "communication_id")
    private int communicationId;

    @Column(name = "message")
    private String messageBody;

    @Column(name = "by_user")
    private String sentFrom;

    @Column(name = "to_user")
    private String sentTo;

    @Column(name = "sent_date")
    private Date sentDateTime;

    @Column(name = "read_indicator")
    private String readIndicator;

}
