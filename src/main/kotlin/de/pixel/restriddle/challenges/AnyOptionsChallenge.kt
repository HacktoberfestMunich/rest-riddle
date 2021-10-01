package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("anyOptions")
class AnyOptionsChallenge(@Qualifier("tryagain") nextChallenge: ChallengeController) : ChallengeController(ENTRYPOINT, "More options", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "KHV8vhIwGcDaBHDS43Em"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(@RequestParam allParams: Map<String, String>): ResponseEntity<String> {
        return if (allParams.isNotEmpty()) {
            getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "You crazy bitch!").build()
        } else {
            return getPage().addElement("Give me more options!").build()
        }
    }
}
