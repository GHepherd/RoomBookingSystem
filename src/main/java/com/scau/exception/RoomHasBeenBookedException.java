package com.scau.exception;

public class RoomHasBeenBookedException extends BaseException {
    public RoomHasBeenBookedException() {
        super("该会议室已被预订");
    }
    public RoomHasBeenBookedException(String message) {
        super(message);
    }

}
