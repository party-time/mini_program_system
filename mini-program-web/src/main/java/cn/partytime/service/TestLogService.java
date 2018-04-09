package cn.partytime.service;

import cn.partytime.WechatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TestLogService {


    public Mono<ServerResponse> testLog(ServerRequest request){
        log.info("testLog");
        Mono<WechatDto> result = WebClient.create("http://192.168.1.199:8899").get().uri("/getWechatUserByOpenId/oze02wf3rqwMcB8jAVwqqWLja3Rw")
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(WechatDto.class);

        return ServerResponse.ok().body(result,WechatDto.class);

    }

}
