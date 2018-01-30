package cn.partytime.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.CacheControl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    private Environment environment;

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加日期格式化转换
        DateFormatter dateFormatter = new DateFormatter(DATE_FORMAT);
        registry.addFormatter(dateFormatter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/").setCacheControl(CacheControl.noCache());
    }

    @Bean
    public HttpServer httpServer(RouterFunction<?> routerFunction) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("0.0.0.0", Integer.valueOf(environment.getProperty("server.port")));
        server.newHandler(adapter);
        return server;
    }
}
