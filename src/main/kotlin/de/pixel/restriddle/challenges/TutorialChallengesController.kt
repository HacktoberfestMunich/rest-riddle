package de.pixel.restriddle.challenges

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestParam

import HtmlPage

const val level1 = "q6m1HsG2x85HD3S3CGTO"
const val level2 = "lHjKMVVICUFo355UVqWk"
const val level3 = "O5ZdvcloSZ6J42zZh3vy"
const val level4 = "KHV8vhIwGcDaBHDS43Em"
const val level5 = "urCMB0OlZkeU3OXNNFxP"

@Controller
class TutorialChallengesController {

    @GetMapping("/", "/index")
    @ResponseBody
    fun index() = HtmlPage("Rest-Riddle")
        .addHeadline("Rest-Riddle")
        .addHeadline("This is the starting point for the Rest-Riddle of Hacktoberfest 2021.", 3)
        .addLink(level1, "Starting point")
        .build()

    @GetMapping("/" + level1)
    @ResponseBody
    fun level1() = HtmlPage("level1").addHeadline("Level 1").addElement("<p>It starts now, have fun.</p>").addLink(level2, "").build()

    @GetMapping("/" + level2)
    @ResponseBody
    fun level2() = HtmlPage("level2").addImage("uDG7KGAIF4QQLQTdxJQS.png").build()

    private var tryAgainCounter = 0

    @GetMapping("/" + level3)
    @ResponseBody
    fun level3(): ResponseEntity<String> {
        if (tryAgainCounter < 20) {
            tryAgainCounter++
            return HtmlPage("level3").addElement("Try again!").build()
        }
        else
            return HtmlPage("level3").addLink(level4, "This way").build()       
    }
   
    @GetMapping("/" + level4)
    @ResponseBody
    fun level4(@RequestParam allParams: Map<String,String>): ResponseEntity<String> {
        if (allParams.isEmpty())
            return HtmlPage("level4").addElement("Give me more options!").build()
        else
            return HtmlPage("level4").addLink(level5, "You crazy bitch!").build()
    }

    @GetMapping("/" + level5)
    @ResponseBody
    fun level5() = HtmlPage("level5").addElement("\uD83E\uDD73").build()

}
