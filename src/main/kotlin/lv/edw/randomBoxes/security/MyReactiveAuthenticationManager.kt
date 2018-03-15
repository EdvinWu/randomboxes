package lv.edw.randomBoxes.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MyReactiveAuthenticationManager : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        if (authentication == null) {
            throw RuntimeException("Authentication cannot be null")
        }
        return Mono.just(authentication)
    }
}
