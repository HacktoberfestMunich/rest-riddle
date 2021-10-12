package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("deleteme")
class DeleteMeChallenge(@Qualifier("quest") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "A Magic Map", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "w7MTEs2ctCAQsUzBqtlT"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addElement("Enemies are behind you. You find a magic map that shows your tracks.<br>Delete them to avoid that the enemies can chase you down!<br>")
            .addImage("map.png")
            .build()
    }

    @DeleteMapping("/${ENTRYPOINT}")
    fun deleteMe(): ResponseEntity<String> {
        return getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "You escaped!").build()
    }
}
