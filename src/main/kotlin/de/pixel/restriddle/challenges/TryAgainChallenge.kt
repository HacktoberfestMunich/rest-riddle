package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("tryagain")
class TryAgainChallenge(@Qualifier("imageContainer") nextChallenge: ChallengeController) : ChallengeController(ENTRYPOINT, "Try Again Challenge", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "4E4yXR2USj7m9GxL"
        private const val TRY_COUNTS = 20
    }

    private var tryAgainCounter = 0

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        val page = getPage()

        return if (tryAgainCounter < TRY_COUNTS) {
            tryAgainCounter++
            page.addElement("Try again!").build()
        } else {
            page.addLink(nextChallenge?.entrypoint.orEmpty(), "Next").build()
        }
    }
}
