package cn.partytime.repository;

import cn.partytime.model.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@EnableReactiveMongoRepositories
public interface WechatUserRepository extends ReactiveSortingRepository<WechatUser,String> {

    Mono<WechatUser> findByOpenId(String openId);

    Flux<WechatUser> findByNickLike(String nick,Pageable pageable);

    long countBySubscribeTimeBetween(long from, long to);

    Flux<WechatUser> findBySubscribeTimeBetween(long from, long to,Pageable pageable);

    Mono<WechatUser> findByUserId(String userId);

    Flux<WechatUser> findByIdIn(List<String> idList);


}
