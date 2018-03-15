package lv.edw.randomBoxes.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.function.Function

@Component
class MyAuthenticationConverter(private val userDetailsService: MyReactiveUserDetailsService,
                                private val jwtTokenService: JwtTokenService) : Function<ServerWebExchange, Mono<Authentication>> {

    override fun apply(t: ServerWebExchange): Mono<Authentication> {
        val token = getTokenFromRequest(t)
        val usernameFromToken = jwtTokenService.getUsernameFromToken(token)
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null) {
            if (jwtTokenService.isTokenValid(token)) {
                return userDetailsService.findByUsername(usernameFromToken)
                        .publishOn(Schedulers.parallel())
                        .switchIfEmpty(Mono.error(BadCredentialsException("Invalid Credentials")))
                        .map { u -> JwtAuthenticationToken(token, u.username, u.authorities) }
            }
        }
        return Mono.just(authentication)
    }

    private fun getTokenFromRequest(t: ServerWebExchange): String {
        val mutableList = t.request.headers["Authorization"]
        val token = mutableList?.get(0)
        if (token != null) {
            return token
        }
        throw RuntimeException("Auth token is null")
    }
}