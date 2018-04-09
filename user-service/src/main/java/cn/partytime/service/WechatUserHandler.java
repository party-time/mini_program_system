package cn.partytime.service;

import cn.partytime.model.WechatUser;
import cn.partytime.repository.WechatUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class WechatUserHandler {

    @Autowired
    private WechatUserRepository wechatUserRepository;

    public Mono<ServerResponse> getWechatUserById(ServerRequest request){
        return wechatUserRepository.findById(request.pathVariable("id"))
                .flatMap(wechatUser -> ServerResponse.ok().body(Mono.just(wechatUser),WechatUser.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> getWechatUserByOpenId(ServerRequest request) {
        log.info("getWechatUserByOpenId");
        return wechatUserRepository.findByOpenId(request.pathVariable("openId"))
                .flatMap(wechatUser -> ServerResponse.ok().body(Mono.just(wechatUser),WechatUser.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public long countBySubscribeTimeBetween(long from,long to){
        return wechatUserRepository.countBySubscribeTimeBetween(from,to);
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().body(wechatUserRepository.findAll(),WechatUser.class);
    }

    public Mono<ServerResponse> getWechatUserByUserId(ServerRequest request){
        return wechatUserRepository.findByUserId(request.pathVariable("userId"))
                .flatMap(wechatUser -> ServerResponse.ok().body(Mono.just(wechatUser),WechatUser.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getWechatUserByIds(List<String> idList){
        return ServerResponse.ok().body(wechatUserRepository.findByIdIn(idList),WechatUser.class);
    }

}
