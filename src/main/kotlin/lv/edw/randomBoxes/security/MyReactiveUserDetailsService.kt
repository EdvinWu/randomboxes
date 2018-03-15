package lv.edw.randomBoxes.security

import lv.edw.randomBoxes.domain.repos.UserCredsRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MyReactiveUserDetailsService(private val userRepo: UserCredsRepository) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {
        if (username != null) {
            return Mono.just(userRepo.findByUserId(username))
        }
        throw RuntimeException("cannot call on user w/o user id")
    }
}