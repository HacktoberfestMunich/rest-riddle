package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("end")
class ChallengeEnd : ChallengeController(ENTRYPOINT, "You did it!", null) {

    companion object {
        const val ENTRYPOINT = "d9A5z85UKOh139cBBd16"
    }

    @GetMapping(ENTRYPOINT)
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addHeadline("You defeated the dragon. Finally the country can live in peace again.", 2)
            .addImage("/dead-dragon-gf494ecd4d.png", 900)
            .build()
    }
}
