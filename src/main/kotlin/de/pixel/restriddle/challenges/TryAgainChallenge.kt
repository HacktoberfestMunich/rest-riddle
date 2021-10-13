package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RestController
@Qualifier("tryagain")
class TryAgainChallenge(@Qualifier("caesar") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "Tree trunk", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "4E4yXR2USj7m9GxL"
        private const val TRY_COUNTS = 30
    }

    private var tryAgainCounter = 0

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        val page = getPage()

        return if (tryAgainCounter < TRY_COUNTS) {
            tryAgainCounter++
            page
                .addElement("There is a big trunk of a tree in the way. Fortunately you have an axe with you.<br>")
                .addElement("You swing the axe and hit it into the wood.")
                .build()
        } else {
            page.addLink(nextChallenge?.entrypoint.orEmpty(), "Pass the split trunk.").build()
        }
    }
}
