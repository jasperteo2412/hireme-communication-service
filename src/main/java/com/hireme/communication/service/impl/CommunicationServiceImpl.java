package com.hireme.communication.service.impl;

import com.hireme.communication.dto.MessagesDTO;
import com.hireme.communication.entity.MessagesEntity;
import com.hireme.communication.mapper.EntityToDtoMapper;
import com.hireme.communication.repository.CommunicationRepository;
import com.hireme.communication.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Autowired
    private EntityToDtoMapper entityToDtoMapper;
    @Autowired
    private CommunicationRepository communicationRepository;

    public MessagesDTO sendMessage(String userId, MessagesEntity message) {

        Date timestamp = new Date();
        message.setSentDateTime(timestamp);
        message.setReadIndicator("N");

        MessagesEntity result = communicationRepository.save(message);
        MessagesDTO response = entityToDtoMapper.convertMessageEntityToDto(result);

        return response;
    }

    public List<MessagesDTO> getMessages(String userId) {

        List<MessagesEntity> receivedMessages = communicationRepository.findBySentTo(userId);
        List<MessagesEntity> sentMessages = communicationRepository.findBySentFrom(userId);
        List<MessagesEntity> allMessages = new ArrayList<>();
        allMessages.addAll(receivedMessages);
        allMessages.addAll(sentMessages);
        List<MessagesDTO> response = entityToDtoMapper.convertMessageEntityToDto(allMessages);

        return response;
    }

    @Override
    public Long getUnreadMessagesCount(String userId) {
        Long result = communicationRepository.countBySentToAndReadIndicator(userId, "N");

        return result;
    }
}
