package lv.edw.randomBoxes

import lv.edw.randomBoxes.domain.ApplicationRole
import lv.edw.randomBoxes.domain.UserCreds
import lv.edw.randomBoxes.domain.repos.UserCredsRepository
import lv.edw.randomBoxes.model.UserCreationModel
import lv.edw.randomBoxes.security.MyPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserService(private val userCredsRepository: UserCredsRepository,
                  private val myPasswordEncoder: MyPasswordEncoder) {

    fun createUser(userCreationModel: UserCreationModel): Mono<UserCreds> {
        return userCredsRepository.save(mapToEntity(userCreationModel))
    }
/*
    private fun mapCount(count: Long, userCreationModel: UserCreationModel): Mono<UserCreds> {
        if (count > 0) {
            return userCredsRepository.save(mapToEntity(userCreationModel, ))
        }
        return Mono.error(RuntimeException("Already exists ${userCreationModel.username}"))
    }*/

    private fun mapToEntity(userCreationModel: UserCreationModel): UserCreds {
        return UserCreds(
                userId = userCreationModel.username, email = userCreationModel.email,
                enabled = true, expired = false,
                locked = false, pwd = hashPassword(userCreationModel.password),
                roles = Arrays.asList(ApplicationRole("USER")))
    }

    private fun hashPassword(password: String) = myPasswordEncoder.encode(password)

}

