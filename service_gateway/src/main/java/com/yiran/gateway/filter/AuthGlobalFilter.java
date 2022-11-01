package com.yiran.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.yiran.common.JwtUtil;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;
import com.yiran.gateway.config.AuthUrl;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Yang Song
 * @date 2022/11/1 13:42
 */
@Component
public class AuthGlobalFilter implements GlobalFilter {
    private final AuthUrl authUrl;
    public AuthGlobalFilter(AuthUrl authUrl) {
        this.authUrl = authUrl;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String currentUrl = exchange.getRequest().getURI().getPath();
        // 如果访问的资源为不需要加密的资源，直接放行
        if(shouldSkip(currentUrl)){
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        // token验证不通过，直接返回错误信息
        if(!StringUtils.hasLength(token) || !JwtUtil.verify(token)){
            return denyAccess(exchange);
        }
        // token验证通过，在请求头中加入userId给下游服务
        String userId = JwtUtil.getUserId(token);
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("userId", userId)
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    /**
     * 判断访问资源是否需要验证token
     * @param currentUrl 当前路径
     * @return 是否需要跳过
     */
    private boolean shouldSkip(String currentUrl){
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String skipPath : authUrl.getShouldSkipUrls()) {
            if (pathMatcher.match(skipPath, currentUrl)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拦截请求，响应json
     * @param exchange exchange
     * @return Mono
     */
    private Mono<Void> denyAccess(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        //这里在返回头添加编码，否则中文会乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] bytes = JSON.toJSONBytes(R.fail(ResultCodeEnum.NO_AUTH));
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}
