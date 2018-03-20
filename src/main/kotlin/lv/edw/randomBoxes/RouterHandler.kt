package lv.edw.randomBoxes

import lv.edw.randomBoxes.model.AuthModel
import lv.edw.randomBoxes.model.UserCreationModel
import lv.edw.randomBoxes.security.JwtTokenService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class RouterHandler(val jwtTokenService: JwtTokenService,
                    val userService: UserService) {

    fun handleMessage(req: ServerRequest): Mono<ServerResponse> {
        return req.principal().flatMap {
            ServerResponse.ok()
                    .body(Mono.just(AuthenticatedRes(it.name)))
        }
    }

    fun auth(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(req.bodyToMono(AuthModel::class.java)
                .map({ SimpleResp(jwtTokenService.createFromReq(it)) })
                .toMono())
    }

    fun reg(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(req.bodyToMono(UserCreationModel::class.java)
                .flatMap { userService.createUser(it).map { AuthenticatedRes(it.userId) } })
    }
}

data class SimpleResp(val msg: String)

data class AuthenticatedRes(val username: String)