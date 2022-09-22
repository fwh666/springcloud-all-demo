package club.fuwenhao.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 监控计时
 *
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/9 3:39 下午
 */
//@Component
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                final ServerHttpRequest request = exchange.getRequest();
                final MultiValueMap<String, String> queryParams = request.getQueryParams();
                queryParams.forEach((key, value) -> {
                    System.out.println(key + ":" + value);
                });
                return chain.filter(exchange);
            }
        };
        return null;
    }
}
