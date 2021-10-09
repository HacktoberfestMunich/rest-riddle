package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("deleteme")
class DeleteMeChallenge(@Qualifier("fanout") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "Delete me",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "w7MTEs2ctCAQsUzBqtlT"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage().build()
    }

    @DeleteMapping("/${ENTRYPOINT}")
    fun deleteMe(): ResponseEntity<String> {
        return getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "Successfully deleted").build()
    }
}
