package lv.edw.randomBoxes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoRepositories
class RandomBoxesApplication

fun main(args: Array<String>) {
    runApplication<RandomBoxesApplication>(*args)
}

