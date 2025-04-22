package com.scau;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scau.mapper")
public class RoomBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomBookingSystemApplication.class, args);
    }

}
