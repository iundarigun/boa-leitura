package cat.iundarigun.boaleitura

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class BoaLeituraApplication

fun main(args: Array<String>) {
	runApplication<BoaLeituraApplication>(*args)
}
