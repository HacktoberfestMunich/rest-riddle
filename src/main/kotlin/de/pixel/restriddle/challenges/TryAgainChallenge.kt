package de.pixel.restriddle.challenges

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.concurrent.timer
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RestController
@Qualifier("tryagain")
class TryAgainChallenge(@Qualifier("imageContainer") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "Try Again Challenge", nextChallenge) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TryAgainChallenge::class.java)
        const val ENTRYPOINT = "4E4yXR2USj7m9GxL"
        private const val TRY_COUNTS = 20
    }

    private var tryAgainCounter = 0
    private val resetTimer =
        timer("TryAgainResetTimer", true, 0, Duration.minutes(10).inWholeMilliseconds) {
            resetCounter()
        }

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

    private fun resetCounter() {
        LOGGER.debug("Reseting try-again counter")
        tryAgainCounter = 0
    }
}
