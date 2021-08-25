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
const val level6 = "zBm926L337qs7uUyhRV0"
const val level7 = "Xo5xrDQpIJDyW8TlVKUs"
const val level8 = "qTy2lxBrUdvZ0NMlcEwW"
const val level9 = "rSZUgsK56oRY3oYk5OxD"
const val level10 = "d9A5z85UKOh139cBBd16"
const val level11 = "ZiVZAylEDgbB5nObRaGH"


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
            return HtmlPage("level4").addLink(level5 + "?security=sha256", "You crazy bitch!").build()
    }

    @GetMapping("/" + level5)
    @ResponseBody
    fun level5(@RequestParam security: String) {
        if (security in listOf("none", "off")) 
            return HtmlPage("level5").addElement("<a href=\"$level6\">decrypted!</a>").build()
        else if (security.equals("sha1"))
            return HtmlPage("level5").addElement("dc9adf6ea0024b98499b7d5b4ce8300bd838c193").build()
        else if (security.equals("sha256"))
            return HtmlPage("level5").addElement("cd1ba87872f23559e47534e798bd53391861902ab9c957e6d916908165439feb").build()
        else if (security.equals("sha512"))
            return HtmlPage("level5").addElement("571bcf55080d9c416074220a38f17336144bb2136a04c84cd281f6daf9b76372486a05f09efe5237a9240bfae3304d89f7934e42cebffab06b277ed7765d15e6").build()
        else if (security.equals("md5"))
            return HtmlPage("level5").addElement("7744c84d84d08ac0d3ed675f355e199f").build()
        else if (security.equals("base64"))
            return HtmlPage("level5").addElement("PGEgaHJlZj0iekJtOTI2TDMzN3FzN3VVeWhSVjAiPmRlY3J5cHRlZCE8L2E+").build()
        else if (security.equals("crc16"))
            return HtmlPage("level5").addElement("0450").build()
        else if (security.equals("crc32"))
            return HtmlPage("level5").addElement("85f4a83c").build()
        else
            return HtmlPage("level5").addElement("Unsupported!").build()
    }

    @GetMapping("/" + level6)
    @ResponseBody
    fun level6() = HtmlPage("level6").addElement("\uD83E\uDD73").build()

    @GetMapping("/" + level7)
    @ResponseBody
    fun level7() = HtmlPage("level7").addElement("\uD83E\uDD73").build()

    @GetMapping("/" + level8)
    @ResponseBody
    fun level8() = HtmlPage("level8").addElement("\uD83E\uDD73").build()

    @GetMapping("/" + level9)
    @ResponseBody
    fun level9() = HtmlPage("level9").addElement("\uD83E\uDD73").build()

    @GetMapping("/" + level10)
    @ResponseBody
    fun level10() = HtmlPage("level10").addElement("\uD83E\uDD73").build()

    @GetMapping("/" + level11)
    @ResponseBody
    fun level11() = HtmlPage("level11").addElement("\uD83E\uDD73").build()

}
