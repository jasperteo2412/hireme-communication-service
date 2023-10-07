package com.hireme.communication.controller;

import com.hireme.communication.dto.ErrorDTO;
import com.hireme.communication.dto.MessagesDTO;
import com.hireme.communication.dto.ResponseBodyDTO;
import com.hireme.communication.entity.MessagesEntity;
import com.hireme.communication.exception.MessageException;
import com.hireme.communication.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CommunicationController {

    static final Logger logger = Logger.getLogger(CommunicationController.class.getName());

    @Autowired
    private CommunicationService communicationService;

    @PostMapping(value = "/v1/messages")
    private ResponseBodyDTO postMessages(
            @RequestHeader("USER-ID") String userId,
            @RequestBody MessagesEntity newMessage
            ){
        logger.log(Level.FINE, "POST postMessages");

        ResponseBodyDTO response = new ResponseBodyDTO();
        ErrorDTO error = new ErrorDTO();

        try{
            logger.log(Level.FINE, "POST postMessages: Saving Messages...");
            MessagesDTO result = communicationService.sendMessage(userId, newMessage);

            if(result == null){
                logger.log(Level.FINE, "POST postMessages: Error in saving messages...");
                error.setError("Communication Service Error");
                error.setMessage("Failed to sent message");
                error.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
                error.setException("");
                response.setData(null);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setError(error);
            }
            else{
                logger.log(Level.FINE, "POST postMessages: Messages saved");
                response.setData("Message was sent successfully");
                response.setError(null);
                response.setStatus(HttpStatus.OK.value());
                response.setError(null);
            }
        } catch(MessageException e){
            logger.log(Level.INFO, "POST postMessages: Exception error encountered");
            error.setError("Communication Service Error");
            error.setException("Message Exception");
            error.setMessage(e.getMessage());
            error.setErrorCode(e.getErrorCode());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError(error);
        }

        logger.log(Level.FINE, "POST postMessages: completed");
        return response;
    }

    @GetMapping(value = "/v1/messages")
    private ResponseBodyDTO getMessages(@RequestHeader("USER-ID") String userId){

        logger.log(Level.FINE, "GET getMessages");

        ResponseBodyDTO response = new ResponseBodyDTO();
        ErrorDTO error = new ErrorDTO();

        try{
            logger.log(Level.FINE, "GET getMessages: Retrieving Messages...");
            List<MessagesDTO> result = communicationService.getMessages(userId);
            response.setData(result);
            response.setError(null);
            response.setStatus(HttpStatus.OK.value());
            response.setError(null);

        } catch(MessageException e){
            logger.log(Level.INFO, "GET getMessages: Exception error encountered");
            error.setError("Communication Service Error");
            error.setException("Message Exception");
            error.setMessage(e.getMessage());
            error.setErrorCode(e.getErrorCode());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError(error);
        }

        logger.log(Level.FINE, "GET getMessages: completed");
        return response;
    }

    @GetMapping(value = "/v1/messages/check")
    private ResponseBodyDTO getUnreadMessagesCount(@RequestHeader("USER-ID") String userId){

        logger.log(Level.FINE, "GET getUnreadMessagesCount");

        ResponseBodyDTO response = new ResponseBodyDTO();
        ErrorDTO error = new ErrorDTO();

        try{

            logger.log(Level.FINE, "GET getUnreadMessagesCount: Retrieving unread messages count...");
            Long result = communicationService.getUnreadMessagesCount(userId);
            response.setData(result);
            response.setError(null);
            response.setStatus(HttpStatus.OK.value());
            response.setError(null);

        } catch(MessageException e){
            logger.log(Level.INFO, "GET getUnreadMessagesCount: Exception error encountered");
            error.setError("Communication Service Error");
            error.setException("Message Exception");
            error.setMessage(e.getMessage());
            error.setErrorCode(e.getErrorCode());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError(error);
        }

        logger.log(Level.FINE, "GET getUnreadMessagesCount: completed");
        return response;
    }
}
