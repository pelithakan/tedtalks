package tedtalks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TedTalkApplication

fun main(args: Array<String>) {
    runApplication<TedTalkApplication>(*args)
}