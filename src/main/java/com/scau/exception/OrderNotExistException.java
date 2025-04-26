package com.scau.exception;

public class OrderNotExistException extends BaseException{
    public OrderNotExistException() {
        super("订单不存在");
    }
    public OrderNotExistException(String message) {
        super(message);
    }
}
