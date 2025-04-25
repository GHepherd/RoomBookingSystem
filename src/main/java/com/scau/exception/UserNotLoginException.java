package com.scau.exception;

public class UserNotLoginException extends BaseException{
    public UserNotLoginException() {
        super("用户未登录");
    }
    public UserNotLoginException(String message) {
        super(message);
    }
}
