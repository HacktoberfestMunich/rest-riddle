package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("anyOptions")
class AnyOptionsChallenge(@Qualifier("stash") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "The crying old man",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "KHV8vhIwGcDaBHDS43Em"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(@RequestParam allParams: Map<String, String>): ResponseEntity<String> {
        return if (allParams.isNotEmpty()) {
            getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "You crazy bitch!").build()
        } else {
            return getPage().addElement("You continue to follow the narrow path when you suddenly see a badly dressed nervous old man sitting behind the next bend. Options! He cries. Why do I have no options?").addImage("insane_guy.png").build()
        }
    }
}
