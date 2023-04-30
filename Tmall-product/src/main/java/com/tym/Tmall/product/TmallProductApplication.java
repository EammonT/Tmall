package com.tym.Tmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tym.Tmall.product.dao")
public class TmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmallProductApplication.class,args);
    }
}
