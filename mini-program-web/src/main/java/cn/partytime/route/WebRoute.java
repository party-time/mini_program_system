package cn.partytime.route;

import cn.partytime.service.TestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebRoute {

    @Autowired
    private TestLogService testLogService;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {

        return route(GET("/testLog"),testLogService::testLog);

    }
}
