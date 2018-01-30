package cn.partytime.route;

import cn.partytime.service.WechatUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WechatRoute {

    @Autowired
    private WechatUserHandler wechatUserHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(POST("/getWechatUserById/{id}"), wechatUserHandler::getWechatUserById)
                .andRoute(GET("/getWechatUserByOpenId/{openId}"),wechatUserHandler::getWechatUserByOpenId)
                .andRoute(GET("/findAll"),wechatUserHandler::findAll)
                .andRoute(GET("/getWechatUserByUserId/{userId}"),wechatUserHandler::getWechatUserByUserId);
    }


}
