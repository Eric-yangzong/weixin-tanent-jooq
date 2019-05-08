package bdhb.usershiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("bdhb.usershiro")
public class AppShiro {
	public static void main(String[] args) {
		SpringApplication.run(AppShiro.class, args);
	}
}
