package com.hireme.communication.repository;

import com.hireme.communication.entity.MessagesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunicationRepository extends CrudRepository<MessagesEntity, Integer> {
    List<MessagesEntity> findBySentTo(String sentTo);
    List<MessagesEntity> findBySentFrom(String sentFrom);
    long countBySentToAndReadIndicator(String sentTo, String readIndicator);
}
