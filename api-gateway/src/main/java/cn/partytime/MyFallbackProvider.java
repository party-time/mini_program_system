package cn.partytime;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class MyFallbackProvider implements FallbackProvider {


    public ClientHttpResponse fallbackResponse(Throwable throwable) {
        log.error("",throwable);
        if( throwable instanceof HttpHostConnectException){
            return response();
        }
        return response();
    }

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return null;
    }


    private ClientHttpResponse response(){
        return new ClientHttpResponse(){

            @Override
            public InputStream getBody() throws IOException {
                String r = "{\"result\":\"500\"}";
                return new ByteArrayInputStream(r.getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //和body中的内容编码一致，否则容易乱码
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }

            /**
             * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的，
             * 不应该把api的404,500等问题抛给客户端
             * 网关和api服务集群对于客户端来说是黑盒子
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {

                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {

                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {

                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {


            }

        };
    }
}
