package lv.edw.randomBoxes.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.io.Serializable


class JwtAuthenticationEntryPoint : ServerAuthenticationEntryPoint, Serializable {
    override fun commence(exchange: ServerWebExchange?, e: AuthenticationException?): Mono<Void> {
        e?.printStackTrace()
//        exchange?.request?.body
        return Mono.empty()
    }
}