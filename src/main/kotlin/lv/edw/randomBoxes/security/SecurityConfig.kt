package lv.edw.randomBoxes.security

import org.springframework.beans.factory.BeanInitializationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher

@EnableWebFluxSecurity
@Configuration
class SecurityConfig(val authenticationConverter: MyAuthenticationConverter,
                     val reactiveAuthenticationManager: MyReactiveAuthenticationManager) {
    private val entryPoint = JwtAuthenticationEntryPoint()

    @Bean
    fun webFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http.httpBasic().disable()
        http.formLogin().disable()
        http.csrf().disable()
        http.logout().disable()

        http.authenticationManager(reactiveAuthenticationManager)
        http.addFilterAt(apiAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()

    }

    private fun apiAuthenticationWebFilter(): AuthenticationWebFilter {
        try {
            val apiAuthenticationWebFilter = AuthenticationWebFilter(reactiveAuthenticationManager)
            apiAuthenticationWebFilter.setAuthenticationFailureHandler(ServerAuthenticationEntryPointFailureHandler(this.entryPoint))
            apiAuthenticationWebFilter.setAuthenticationConverter(this.authenticationConverter)
            apiAuthenticationWebFilter.setRequiresAuthenticationMatcher(PathPatternParserServerWebExchangeMatcher("/api/**"))

            return apiAuthenticationWebFilter
        } catch (e: Exception) {
            throw BeanInitializationException("Could not initialize AuthenticationWebFilter apiAuthenticationWebFilter.", e)
        }
    }

}