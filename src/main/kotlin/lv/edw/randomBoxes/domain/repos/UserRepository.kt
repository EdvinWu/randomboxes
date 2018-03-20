package lv.edw.randomBoxes.domain.repos

import lv.edw.randomBoxes.domain.UserInfo
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserRepository : ReactiveMongoRepository<UserInfo, String>
