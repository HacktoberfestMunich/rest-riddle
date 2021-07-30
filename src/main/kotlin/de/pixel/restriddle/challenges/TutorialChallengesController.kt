package de.pixel.restriddle.challenges

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class TutorialChallengesController {

    @GetMapping("/", "/index")
    fun index(): String = "redirect:index.html"

    @GetMapping("/fun")
    @ResponseBody
    fun `fun`(): ResponseEntity<String> = ResponseEntity.ok("\uD83E\uDD73")
}
