package cn.partytime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("cn.partytime")
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GameApplication.class);
    }

}
