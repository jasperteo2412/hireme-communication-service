package com.hireme.communication.service;

import com.hireme.communication.dto.MessagesDTO;
import com.hireme.communication.entity.MessagesEntity;

import java.util.List;

public interface CommunicationService {
    public MessagesDTO sendMessage(String userId, MessagesEntity message);
    public List<MessagesDTO> getMessages(String userId);
    public Long getUnreadMessagesCount(String userId);
}
