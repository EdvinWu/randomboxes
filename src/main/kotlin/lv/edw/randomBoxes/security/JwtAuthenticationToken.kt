package lv.edw.randomBoxes.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

data class JwtAuthenticationToken(val token: String, val username: String, private val roles: Collection<GrantedAuthority>) :
        UsernamePasswordAuthenticationToken(username, null, roles)


