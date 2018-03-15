package lv.edw.randomBoxes

import lv.edw.randomBoxes.domain.ApplicationRole
import lv.edw.randomBoxes.domain.repos.UserCreds
import lv.edw.randomBoxes.domain.repos.UserCredsRepository
import lv.edw.randomBoxes.model.UserCreationModel
import lv.edw.randomBoxes.security.MyPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userCredsRepository: UserCredsRepository,
                  private val myPasswordEncoder: MyPasswordEncoder) {

    fun createUser(userCreationModel: UserCreationModel): Boolean {
        val countById = userCredsRepository.countByUserId(userCreationModel.username)
        if (countById == 0) {
            userCredsRepository.save(UserCreds(
                    userId = userCreationModel.username, email = userCreationModel.email,
                    enabled = true, expired = false,
                    locked = false, pwd = hashPassword(userCreationModel.password),
                    role = ApplicationRole("USER")))
            return true
        }
        return false
    }

    private fun hashPassword(password: String) = myPasswordEncoder.encode(password)

}

