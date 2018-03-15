package lv.edw.randomBoxes.domain.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserCredsRepository : CrudRepository<UserCreds, String> {

    fun findByUserId(userId: String): UserDetails
    fun countByUserId(id: String): Int
}