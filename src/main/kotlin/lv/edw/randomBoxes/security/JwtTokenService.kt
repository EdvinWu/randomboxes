package lv.edw.randomBoxes.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lv.edw.randomBoxes.domain.repos.UserCredsRepository
import lv.edw.randomBoxes.model.AuthModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

@Service
class JwtTokenService(@Value("\${jwt.secret}") private val secret: String,
                      val userCredsRepository: UserCredsRepository,
                      val myPasswordEncoder: MyPasswordEncoder) {

    fun createFromReq(authModel: AuthModel): String {
        if (isValidCredentials(authModel)) {
            return createJwtToken(HashMap(), authModel.userId, authModel.expirationOffset)
        }
        throw RuntimeException("wrong credentials")
    }

    private fun isValidCredentials(authModel: AuthModel): Boolean {
        val userCreds = userCredsRepository.findByUserId(authModel.userId)
        val encodedPassword = userCreds.block()?.password
        if (myPasswordEncoder.matches(authModel.password, encodedPassword)) {
            return true
        }
        return false
    }

    private fun createJwtToken(claims: Map<String, Any>, username: String, expirationOffset: Long): String {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(getCurrentTime())
                .setExpiration(getExpirationDate(expirationOffset))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(getCurrentTime())
    }

    fun isTokenValid(token: String) = getExpirationDateFromToken(token).after(getCurrentTime())


    fun getUsernameFromToken(token: String): String = getClaimFromToken(token, Function { it.subject })


    private fun getIssuedAtDateFromToken(token: String): Date = getClaimFromToken(token, Function { it.issuedAt })


    private fun getExpirationDateFromToken(token: String): Date = getClaimFromToken(token, Function { it.expiration })


    private fun getExpirationDate(expirationOffset: Long) = getCurrentTime() + expirationOffset

    private fun getCurrentTime() = Date()
}

private operator fun Date.plus(expirationOffset: Long): Date = Date(this.time + expirationOffset * 1000)
