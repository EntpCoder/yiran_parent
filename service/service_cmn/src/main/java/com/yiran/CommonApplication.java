package com.yiran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class CommonApplication {
    public static void main( String[] args ) {
        SpringApplication.run(CommonApplication.class,args);
    }
}
