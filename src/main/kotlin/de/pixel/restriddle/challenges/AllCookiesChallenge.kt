package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@Qualifier("allCookies")
class AllCookiesChallenge(@Qualifier("userAgent") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "A wild monster appears...", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "gQbFIbllHK6bjlQ95A22"
        private const val COOKIE_YOUTUBE = "https://www.youtube.com/watch?v=Ye8mB6VsUHw"
        private const val COOKIE_COUNT = 5
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(servletRequest: HttpServletRequest): ResponseEntity<String> {
        val cookies = servletRequest.cookies
        return if (cookies != null && cookies.count() > COOKIE_COUNT) {
            getPage().addElement("Well done!").addLink(nextChallenge?.entrypoint.orEmpty(), "Continue").build()
        } else {
            getPage().addLink(COOKIE_YOUTUBE, "Watch me", true).addElement("We need at least $COOKIE_COUNT").build()
        }
    }
}
