package com.bdhanbang.weixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bdhanbang")
public class WeiXinApp {
	public static void main(String[] args) {
		SpringApplication.run(WeiXinApp.class, args);
	}

}
