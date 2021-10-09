package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("hiddenLink")
class HiddenLinkChallenge(@Qualifier("disabledButton") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "Find me", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "q6m1HsG2x85HD3S3CGTO"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage().addElement("Challenges are starting now.<br> Have fun.").addLink(nextChallenge?.entrypoint.orEmpty(), "").build()
    }


}
