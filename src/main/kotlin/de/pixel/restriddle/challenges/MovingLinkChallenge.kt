package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("movingLink")
class MovingLinkChallenge(@Qualifier("userAgent") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "Catch me",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "jvde3Ciq6aA8JIHN40"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addLink(nextChallenge?.entrypoint.orEmpty(), "Next")
            .addScript(
                """
                let link = document.getElementsByTagName("a")[0];
                link.style.position = "absolute";
                link.style.margin = "16px";
                link.addEventListener('mouseenter', e => {                    
                    link.style.right = (Math.random() * 1000).toString() + "px";
                    link.style.top = (Math.random() * 1000).toString() + "px";
                });
            """.trimIndent()
            )
            .build()
    }
}
