package lv.edw.randomBoxes.domain

import lv.edw.randomBoxes.model.Gender
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserInfo(@Id val userId: String,
                    val firstName: String?,
                    val lastName: String?,
                    val gender: Gender,
                    val age: Long
)