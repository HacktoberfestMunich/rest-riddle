package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("userAgent")
class UserAgentChallenge(nextChallenge: BeltEnding) : ChallengeController(
    ENTRYPOINT,
    "Dwarf Guard", nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "6UakTRiHRsfpnPw8RNly"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(@RequestHeader(value = "User-Agent") userAgent: String): ResponseEntity<String> {
        return if (userAgent.contains("Herald", true)) {
            getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "You may proceed.").addImage("guard.png").build()
        } else {
            var agent = userAgent
            try {
                agent = userAgent.replace(" +\\([^)]+\\)".toRegex(), "")
                agent = agent.replace(" +\\([^)]+\\)".toRegex(), "")
                agent = agent.split(" ")[2].split("/")[0]
            } catch (e: Exception) {}

            getPage().addElement("<p>A guard blocks the way.<br>\"Who are you ${agent}? Never heard of such a strange name... Only guards may pass.\"</p>").addImage("guard.png").build()
        }
    }
}
