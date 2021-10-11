package de.pixel.restriddle.challenges

import HtmlPage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

abstract class ItemEnding(entrypoint: String, private val imagePath: String, private val itemname: String) : ChallengeController(
    entrypoint, "", null
) {

    override fun getPage() = HtmlPage("You reached a blessed item.")
        .addHeadline("You reached a blessed item.")
        .addImage(imagePath)
        .addElement(
            """
            <p>You found the "$itemname". This will be useful to defeat a dragon.</p>
        """.trimIndent()
        )
}

@RestController
class SwordEnding : ItemEnding(ENTRYPOINT, "sword-1nvafvo9h4.png", QuestChallenge.SWORD_NAME) {

    companion object {
        const val ENTRYPOINT = "JZfckeKT25PZNMc86cML"
    }

    @GetMapping("/$ENTRYPOINT")
    fun ending(): ResponseEntity<String> {
        return getPage().build()
    }
}

@RestController
class ArmorEnding : ItemEnding(ENTRYPOINT, "shield-g9c1560ef2.png", QuestChallenge.ARMOR_NAME) {

    companion object {
        const val ENTRYPOINT = "lVeUrV7MAObziN8bp4tO"
    }

    @GetMapping("/$ENTRYPOINT")
    fun ending(): ResponseEntity<String> {
        return getPage().build()
    }
}

@RestController
class PotionEnding : ItemEnding(ArmorEnding.ENTRYPOINT, "potion-gd54e796d7.png", QuestChallenge.POTIONS_NAME) {

    companion object {
        const val ENTRYPOINT = "RNRSmcunaCbOXXpjzfI2"
    }

    @GetMapping("/$ENTRYPOINT")
    fun ending(): ResponseEntity<String> {
        return getPage().build()
    }
}

@RestController
class BeltEnding : ItemEnding(ArmorEnding.ENTRYPOINT, "purple-belt-gb2d6f62ec.png", QuestChallenge.BELT_NAME) {

    companion object {
        const val ENTRYPOINT = "70Tk5vGUXkomUhlJewac"
    }

    @GetMapping("/$ENTRYPOINT")
    fun ending(): ResponseEntity<String> {
        return getPage().build()
    }
}
