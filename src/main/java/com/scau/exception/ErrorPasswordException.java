package com.scau.exception;

public class ErrorPasswordException extends BaseException{
    public ErrorPasswordException() {
        super("密码错误");
    }
    public ErrorPasswordException(String message) {
        super(message);
    }
}
