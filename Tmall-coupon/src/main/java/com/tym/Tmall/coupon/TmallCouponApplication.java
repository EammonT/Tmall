package com.tym.Tmall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmallCouponApplication.class,args);
    }
}
