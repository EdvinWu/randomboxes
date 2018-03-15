package lv.edw.randomBoxes.domain

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.Id

data class ApplicationRole(private val role: String) : GrantedAuthority {
    override fun getAuthority(): String = role
}