package cn.partytime.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;


@Component
public class MyWebSocketHandler implements WebSocketHandler {

    @Autowired
    private MsgService msgService;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        System.out.println(msgService.toString());
        //MsgService msgService = new MsgService();
        return webSocketSession.send(
                webSocketSession.receive().map(WebSocketMessage::getPayloadAsText).map(msgService::handleInboundMessage)
                        .map(webSocketSession::textMessage)
        );
    }


}
