package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("end")
class ChallengeEnd : ChallengeController(ENTRYPOINT, "End of line", null) {

    companion object {
        const val ENTRYPOINT = "d9A5z85UKOh139cBBd16"
    }

    @GetMapping(ENTRYPOINT)
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addHeadline("Your reached one end of the challenges. But there might be more!", 2)
            .addHeadline("\uD83E\uDD73").build()
    }


}