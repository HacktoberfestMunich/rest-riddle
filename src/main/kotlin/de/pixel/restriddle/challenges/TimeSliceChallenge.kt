package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@Qualifier("timeslice")
class TimeSliceChallenge(@Qualifier("fanout") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "Closing doors",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "4dVjE06G0LTtNycdkj4f"
        private const val VALID_SECOND = 42
        private const val VALID_DURATION = 5
        private const val HINT_RANGE = 5
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        val currentSecond = LocalTime.now().second

        return if (isInValidRange(currentSecond)) {
            getPage()
                .addLink(nextChallenge?.entrypoint.orEmpty(), "You shall pass!")
                .build()
        } else if (isInValidRange(currentSecond + HINT_RANGE)) {
            getPage()
                .addElement("You will soon pass!")
                .build()
        } else {
            getPage()
                .addElement("You shall not pass!")
                .build()
        }
    }

    private fun isInValidRange(second: Int): Boolean = second > VALID_SECOND && second < VALID_SECOND + VALID_DURATION
}
