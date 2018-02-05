package cn.partytime;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class MyGatewayfilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //后端api出现404转成json
        int status = ctx.getResponse().getStatus();
        if(status == 404){
            ctx.setResponseBody("{\"result\":500}");
            ctx.getResponse().setStatus(200);
        }
        return null;
    }

}
