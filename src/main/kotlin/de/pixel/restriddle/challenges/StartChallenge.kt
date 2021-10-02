package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class StartChallenge(@Qualifier("hiddenLink") nextChallenge: ChallengeController) : ChallengeController(ENTRYPOINT, "Rest-Riddle", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "/"
    }

    @GetMapping(ENTRYPOINT)
    fun challenge(servletRequest: HttpServletRequest): ResponseEntity<String> {
        clearCookies(servletRequest)
        return getPage()
            .addHeadline("This is the starting point for the Rest-Riddle of Hacktoberfest 2021.", 2)
            .addLink(nextChallenge?.entrypoint.orEmpty(), "First Challenge").build()
    }

    private fun clearCookies(servletRequest: HttpServletRequest) {
        servletRequest.cookies?.forEach {
            it.maxAge = 0
        }
    }
}
