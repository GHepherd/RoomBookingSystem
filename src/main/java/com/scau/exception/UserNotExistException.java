package com.scau.exception;

public class UserNotExistException extends BaseException{
    public UserNotExistException() {
        super("用户不存在");
    }
    public UserNotExistException(String message) {
        super(message);
    }
}
