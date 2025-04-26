package com.scau.exception;

public class SubmitBookingErrorException extends BaseException{
    public SubmitBookingErrorException() {
        super("预定失败，请重试");
    }
    public SubmitBookingErrorException(String message) {
        super(message);
    }
}
