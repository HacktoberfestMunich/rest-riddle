package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("userAgent")
class UserAgentChallenge(@Qualifier("deleteme") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT,
    "We know what you are using", nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "6UakTRiHRsfpnPw8RNly"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(@RequestHeader(value = "User-Agent") userAgent: String): ResponseEntity<String> {
        return if (userAgent.contains("power-pc", true)) {
            getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "You may proceed.").build()
        } else {
            getPage().addElement("<p>Only <strong>Power-PC</strong> is supported for this challenge.</p>").build()
        }
    }
}
