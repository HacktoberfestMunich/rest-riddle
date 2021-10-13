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
class KeypadChallenge(@Qualifier("tryagain") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "Have you heard about the keypad?",
    nextChallenge
) {

    companion object {
        const val PASSWORD_SESSION_KEY = "Combo Lock"
        const val ENTRYPOINT = "uQUpNG1dVvaIO8aX21DS"
        const val INITIAL_PASSWORD = "____"
        private const val PASSWORD = "1337"
    }

    @GetMapping("/${ENTRYPOINT}/key/{number}")
    fun keypad(@PathVariable number: Int, servletRequest: HttpServletRequest): ResponseEntity<String> {
        var password = servletRequest.session.getAttribute(PASSWORD_SESSION_KEY)
        if (password == null) {
            password = INITIAL_PASSWORD
            servletRequest.session.setAttribute(PASSWORD_SESSION_KEY, password)
        }
        val oldPassword = password as String

        var newPass = (oldPassword + number)
        newPass = newPass.substring(newPass.length - PASSWORD.length)
        servletRequest.session.setAttribute(PASSWORD_SESSION_KEY, newPass)

        return if (newPass == PASSWORD) {
            getPage()
                .addLink(nextChallenge?.entrypoint.orEmpty(), "Open the door")
                .build()
        } else {
            getPage()
                .addElement("You find a door saying \"Only the leet may pass!\".<br>It is locked by a combination lock. <br>")
                .addImage("/combolock.png")
                .addElement("Combination:<br>")
                .addElement("<pre>${newPass}</pre>")
                .build()
        }
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(servletResponse: HttpServletResponse) {
        servletResponse.sendRedirect("/${ENTRYPOINT}/key/1")
    }
}
