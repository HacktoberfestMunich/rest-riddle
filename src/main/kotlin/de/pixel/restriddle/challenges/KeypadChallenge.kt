package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("keypad")
class KeypadChallenge(nextChallenge: ArmorEnding) : ChallengeController(ENTRYPOINT, "Have you heard about the keypad?", nextChallenge) {

    companion object {
        const val PASSWORD_SESSION_KEY = "password"
        const val ENTRYPOINT = "uQUpNG1dVvaIO8aX21DS"
        const val INITIAL_PASSWORD = "____"
        private const val PASSWORD = "1337"
    }

    @GetMapping("/${ENTRYPOINT}/keypad/{number}")
    fun keypad(@PathVariable number: Int, servletRequest: HttpServletRequest): ResponseEntity<String> {
        var password = servletRequest.session.getAttribute(PASSWORD_SESSION_KEY)
        if (password == null) {
            password = INITIAL_PASSWORD
            servletRequest.session.setAttribute(PASSWORD_SESSION_KEY, password)
        }
        val oldPassword = password as String

        val newPass = oldPassword.drop(1) + number
        servletRequest.session.setAttribute(PASSWORD_SESSION_KEY, newPass)

        return if (newPass == PASSWORD) {
            getPage()
                .addLink(nextChallenge?.entrypoint.orEmpty(), "Follow the bird")
                .build()
        } else {
            getPage()
                .addElement("")
                .addHeadline("Password:", 2)
                .addElement("<pre>${newPass}</pre>")
                .build()
        }
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(servletResponse: HttpServletResponse) {
        servletResponse.sendRedirect("/${ENTRYPOINT}/keypad/1")
    }
}
