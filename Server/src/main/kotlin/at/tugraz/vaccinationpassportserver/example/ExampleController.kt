package at.tugraz.vaccinationpassportserver.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.security.Principal

@RestController
@RequestMapping("/example")
class ExampleController {
    @GetMapping()
    fun example(principal: Principal): List<Example> = listOf(
            Example("1", "Hello ${principal.name}!"),
            Example("2", "Bonjour ${principal.name}!"),
            Example("3", "Privet ${principal.name}!"),
    )
}