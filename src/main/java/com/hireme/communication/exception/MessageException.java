package com.hireme.communication.exception;

import lombok.Getter;

@Getter
public class MessageException extends RuntimeException{
    private final String errorCode;
    private final String encounteredBy;

    public MessageException(final String message, final Throwable cause, String errorCode, String encounteredBy){
        super(message, cause);
        this.errorCode = errorCode;
        this.encounteredBy = encounteredBy;
    }

    public MessageException(final Throwable cause, String errorCode, String encounteredBy){
        super(cause);
        this.errorCode = errorCode;
        this.encounteredBy = encounteredBy;
    }

    public MessageException(final String message){
        super(message);
        this.errorCode = null;
        this.encounteredBy = null;
    }

    public MessageException(){
        super();
        this.errorCode = null;
        this.encounteredBy = null;
    }
}
