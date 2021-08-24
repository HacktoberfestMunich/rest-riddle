package de.pixel.restriddle.challenges

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TutorialChallengesController {

    @GetMapping("/", "/index")
    fun index(): String = "redirect:index.html"

    @GetMapping("/lHjKMVVICUFo355UVqWk")
    @ResponseBody
    fun `lHjKMVVICUFo355UVqWk`(): ResponseEntity<String> = ResponseEntity.ok("<img src=\"goodjob.png\">")

    var tryAgainCounter: Int = 0

    @GetMapping("/O5ZdvcloSZ6J42zZh3vy")
    @ResponseBody
    fun `O5ZdvcloSZ6J42zZh3vy`(): ResponseEntity<String> {
        if (tryAgainCounter < 20) {
            tryAgainCounter++
            return ResponseEntity.ok("Try again!")
        }
        else
            return ResponseEntity.ok("<a href=\"KHV8vhIwGcDaBHDS43Em\">This way</a>")        
    }
   
    @GetMapping("/KHV8vhIwGcDaBHDS43Em")
    @ResponseBody
    fun `KHV8vhIwGcDaBHDS43Em`(@RequestParam allParams: Map<String,String>): ResponseEntity<String> {
        if (allParams.isEmpty())
            return ResponseEntity.ok("Give me more options!")
        else
            return ResponseEntity.ok("<a href=\"urCMB0OlZkeU3OXNNFxP\">You crazy bitch!</a>")        
    }

    @GetMapping("/urCMB0OlZkeU3OXNNFxP")
    @ResponseBody
    fun `urCMB0OlZkeU3OXNNFxP`(): ResponseEntity<String> = ResponseEntity.ok("\uD83E\uDD73")

}
