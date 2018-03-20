package lv.edw.randomBoxes.domain.repos

import lv.edw.randomBoxes.domain.UserCreds
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

interface UserCredsRepository : ReactiveMongoRepository<UserCreds, String> {

    fun findByUserId(userId: String): Mono<UserDetails>
    fun countByUserId(id: String): Mono<Long>
}