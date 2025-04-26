package com.scau.exception;

public class UserAlreadyExistException extends BaseException{
    public UserAlreadyExistException() {
        super("用户已存在");
    }
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
