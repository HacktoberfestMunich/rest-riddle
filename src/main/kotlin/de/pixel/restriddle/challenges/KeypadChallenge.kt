package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@Qualifier("keypad")
class KeypadChallenge(nextChallenge: ArmorEnding) : ChallengeController(ENTRYPOINT, "Have you heard about the keypad?", nextChallenge) {

    companion object {
        const val PASSWORD_SESSION_KEY = "password"
        const val ENTRYPOINT = "uQUpNG1dVvaIO8aX21DS"
        const val INITIAL_PASSWORD = "____"
    }

    @GetMapping("/${ENTRYPOINT}/keypad/{number}")
    fun keypad(@PathVariable number: Int, servletRequest: HttpServletRequest): ResponseEntity<String> {
        val oldPass = servletRequest.session.getAttribute(PASSWORD_SESSION_KEY) as String
        val newPass = oldPass.drop(1) + number
        servletRequest.session.setAttribute(PASSWORD_SESSION_KEY, newPass)

        return getPage().addElement("Typed $number").build()
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(servletRequest: HttpServletRequest): ResponseEntity<String> {
        var password = servletRequest.session.getAttribute(PASSWORD_SESSION_KEY)
        if (password == null) {
            password = INITIAL_PASSWORD
            servletRequest.session.setAttribute(PASSWORD_SESSION_KEY, password)
        }
        return getPage().addLink("/${ENTRYPOINT}/keypad/1", "Have you heard?", true)
            .addHeadline("Password:", 2)
            .addElement("<pre data-valid=\"4757\">${password}</pre>")
            .build()
    }
}
