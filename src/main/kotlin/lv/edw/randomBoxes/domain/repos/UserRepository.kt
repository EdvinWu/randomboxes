package lv.edw.randomBoxes.domain.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserInfo, String>
