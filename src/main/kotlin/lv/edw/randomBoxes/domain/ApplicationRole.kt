package lv.edw.randomBoxes.domain

import org.springframework.security.core.GrantedAuthority

data class ApplicationRole(private val role: String) : GrantedAuthority {
    override fun getAuthority(): String = role
}