package lv.edw.randomBoxes.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
data class UserCreds(@Id val userId: String,
                     val email: String,
                     val pwd: String,
                     val expired: Boolean,
                     val enabled: Boolean,
                     val locked: Boolean,
                     val roles: MutableCollection<ApplicationRole>
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles

    override fun isEnabled(): Boolean = enabled

    override fun getUsername(): String = userId

    override fun isCredentialsNonExpired(): Boolean = expired

    override fun getPassword(): String = pwd

    override fun isAccountNonExpired(): Boolean = expired

    override fun isAccountNonLocked(): Boolean = locked
}
