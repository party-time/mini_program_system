package cn.partytime.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;


@Configuration
@RequiredArgsConstructor
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private final Environment environment;

    @Override
    public MongoClient reactiveMongoClient() {
        int port = environment.getProperty("mongo.port", Integer.class);
        String ip = environment.getProperty("mongo.ip", String.class);
        return MongoClients.create(String.format("mongodb://%s:%d", ip,port));
    }

    @Override
    protected String getDatabaseName() {
        return "user";
    }

    /**
     * 当需要使用多数据源的时候需要配置
    @Override
    protected Collection<String> getMappingBasePackages(){
        return Collections.singleton("");
    }
    **/

}
