package com.hireme.communication.controller;

import com.hireme.communication.dto.ErrorDTO;
import com.hireme.communication.dto.ResponseBodyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunicationController {

    @PostMapping(value = "/messages")
    private ResponseBodyDTO PostMessages(){
        ResponseBodyDTO response = new ResponseBodyDTO();
        ErrorDTO error = new ErrorDTO();

        response.setError(error);
        return response;
    }

    @GetMapping(value = "/messages")
    private ResponseBodyDTO GetMessages(){
        ResponseBodyDTO response = new ResponseBodyDTO();
        ErrorDTO error = new ErrorDTO();

        response.setError(error);
        return response;
    }

}
