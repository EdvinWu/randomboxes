package lv.edw.randomBoxes.model


data class AuthModel(val userId: String, val password: String, val expirationOffset: Long)

data class UserCreationModel(val username: String, val password: String, val email: String)

enum class Gender {
    MALE, FEMALE, NOT_DEFINED
}