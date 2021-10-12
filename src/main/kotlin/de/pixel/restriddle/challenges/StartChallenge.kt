package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class StartChallenge(@Qualifier("hiddenLink") nextChallenge: ChallengeController) : ChallengeController(ENTRYPOINT, "Rest - A Riddle", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "/"
    }

    @GetMapping(ENTRYPOINT)
    fun challenge(servletRequest: HttpServletRequest): ResponseEntity<String> {
        clearCookies(servletRequest)
        return getPage()
            .addHeadline("Welcome strangers!", 2)
            .addElement("We have chosen because it is said that you are the strongest and smartest women and man in the country.<br>")
            .addElement("Please help us solve the challanges to kill the beast that threatens the land.<br>")
            .addLink(nextChallenge?.entrypoint.orEmpty(), "Follow the path...").build()
    }

    private fun clearCookies(servletRequest: HttpServletRequest) {
        servletRequest.cookies?.forEach {
            it.maxAge = 0
        }
    }
}
