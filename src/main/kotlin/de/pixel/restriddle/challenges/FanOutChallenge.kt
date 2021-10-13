package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("fanout")
class FanOutChallenge(
    @Qualifier("dollar") controller1: ChallengeController,
    @Qualifier("imageContainer") controller2: ChallengeController,
    @Qualifier("allCookies") controller3: ChallengeController
) : ChallengeController(ENTRYPOINT, "Crossroads", null) {

    companion object {
        const val ENTRYPOINT = "XdG2qytTadoo97fG"
    }

    private val controllers = mutableListOf<ChallengeController>();

    init {
        controllers.add(controller1)
        controllers.add(controller2)
        controllers.add(controller3)
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addElement("I wonder where these paths will lead me...")
            .addLink(controllers.random().entrypoint, "...choose a way")
            .build()
    }


}
