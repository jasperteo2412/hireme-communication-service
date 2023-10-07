package com.hireme.communication.mapper;

import com.hireme.communication.dto.MessagesDTO;
import com.hireme.communication.entity.MessagesEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {

    MessagesDTO convertMessageEntityToDto(MessagesEntity messagesEntity);
    List<MessagesDTO> convertMessageEntityToDto(List<MessagesEntity> messagesEntityList);
}
