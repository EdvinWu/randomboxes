package lv.edw.randomBoxes.domain.repos

import lv.edw.randomBoxes.model.Gender
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserInfo(@Id val userId: String,
                    val firstName: String?,
                    val lastName: String?,
                    val gender: Gender,
                    val age: Long
)