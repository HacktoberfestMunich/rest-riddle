package de.pixel.restriddle.challenges

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RequestParam

class HtmlPage {

    private var prefix = ""
    private val suffix = "</body>\n</html>"

    private val elements = mutableListOf<String>()

    constructor(title: String = "") {
        prefix = "<!DOCTYPE html>\n"
        prefix += "<html lang=\"en\">\n"
        prefix += "<head>\n"
        prefix += "    <meta charset=\"UTF-8\">\n"
        if (title != "")
            prefix += "    <title>$title</title>\n"
        prefix += "</head>\n"
        prefix += "<body>\n"
    }
    
    fun addElement(element: String): HtmlPage {
        elements.add(element)
        return this
    }


    fun addImage(path: String): HtmlPage = addElement("<img src=\"$path\">")
    fun addLink(path: String, description: String): HtmlPage = addElement("<a href=\"$path\">$description</a>")
    fun addHeadline(content: String, level: Int = 1): HtmlPage = addElement("<h$level>$content</h$level>")

    override fun toString(): String = prefix + elements.joinToString(separator = "\n") + suffix
}

const val level1 = "q6m1HsG2x85HD3S3CGTO"
const val level2 = "lHjKMVVICUFo355UVqWk"
const val level3 = "O5ZdvcloSZ6J42zZh3vy"
const val level4 = "KHV8vhIwGcDaBHDS43Em"
const val level5 = "urCMB0OlZkeU3OXNNFxP"

@Controller
class TutorialChallengesController {

    @GetMapping("/", "/index")
    @ResponseBody
    fun index(): ResponseEntity<String> = ResponseEntity.ok(
        HtmlPage("Rest-Riddle")
        .addHeadline("Rest-Riddle")
        .addHeadline("This is the starting point for the Rest-Riddle of Hacktoberfest 2021.", 3)
        .addLink(level1, "Starting point")
        .toString())

    @GetMapping("/" + level1)
    @ResponseBody
    fun level1(): ResponseEntity<String> = ResponseEntity.ok(
        HtmlPage("level1")
        .addHeadline("Level 1")
        .addElement("<p>It starts now, have fun.</p>")
        .addLink(level2, "")
        .toString())

    @GetMapping("/" + level2)
    @ResponseBody
    fun level2(): ResponseEntity<String> = ResponseEntity.ok(HtmlPage("level2").addImage("uDG7KGAIF4QQLQTdxJQS.png").toString())

    private var tryAgainCounter = 0

    @GetMapping("/" + level3)
    @ResponseBody
    fun level3(): ResponseEntity<String> {
        if (tryAgainCounter < 20) {
            tryAgainCounter++
            return ResponseEntity.ok(HtmlPage("level3").addElement("Try again!").toString())
        }
        else
            return ResponseEntity.ok(HtmlPage("level3").addLink(level4, "This way").toString())        
    }
   
    @GetMapping("/" + level4)
    @ResponseBody
    fun level4(@RequestParam allParams: Map<String,String>): ResponseEntity<String> {
        if (allParams.isEmpty())
            return ResponseEntity.ok(HtmlPage("level4").addElement("Give me more options!").toString())
        else
            return ResponseEntity.ok(HtmlPage("level4").addLink(level5, "You crazy bitch!").toString())
    }

    @GetMapping("/" + level5)
    @ResponseBody
    fun level5(): ResponseEntity<String> = ResponseEntity.ok(HtmlPage("level5").addElement("\uD83E\uDD73").toString())

}
