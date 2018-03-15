package lv.edw.randomBoxes

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig(private val handler: RouterHandler) {

    @Bean
    fun routes() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/api".nest {
                GET("/message", handler::handleMessage)
            }
            "/auth".nest {
                POST("/getToken", handler::auth)
                POST("/createUser", handler::reg)
            }
        }
    }
}
