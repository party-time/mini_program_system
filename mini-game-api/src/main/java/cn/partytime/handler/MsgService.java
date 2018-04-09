package cn.partytime.handler;


import org.springframework.stereotype.Service;

@Service
public class MsgService {

    public String handleInboundMessage(String msg){
        msg = "Response From Server:" + msg;
        return msg;
    }
}
