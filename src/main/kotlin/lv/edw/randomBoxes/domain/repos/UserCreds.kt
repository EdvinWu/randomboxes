package lv.edw.randomBoxes.domain.repos

import lv.edw.randomBoxes.domain.ApplicationRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserCreds(@Id val userId: String,
                     val email: String,
                     val pwd: String,
                     val expired: Boolean,
                     val enabled: Boolean,
                     val locked: Boolean,
                     val role: ApplicationRole
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = Arrays.asList(role)

    override fun isEnabled(): Boolean = enabled

    override fun getUsername(): String = userId

    override fun isCredentialsNonExpired(): Boolean = expired

    override fun getPassword(): String = pwd

    override fun isAccountNonExpired(): Boolean = expired

    override fun isAccountNonLocked(): Boolean = locked
}
