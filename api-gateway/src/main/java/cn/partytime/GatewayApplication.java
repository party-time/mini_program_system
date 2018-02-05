package cn.partytime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    @Bean
    public MyGatewayfilter myGatewayfilter(){
        return new MyGatewayfilter();
    }

    @Bean
    public MyFallbackProvider myFallbackProvider(){
        return new MyFallbackProvider();
    }


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
