package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("disabledButton")
class DisabledButtonChallenge(@Qualifier("deleteme") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "A magic door....",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "XbvUOiA8iNmHrtIcnDZf"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addElement("It seems to be locked.<br>")
            .addElement(
                """
                <form action="$ENTRYPOINT/post" method="post">
                  <input type="submit" value="open" disabled>
                </form>
            """.trimIndent()
            )
            .build()
    }

    @GetMapping("/${ENTRYPOINT}/post")
    fun redirect(response: HttpServletResponse) {
        response.sendRedirect("/${ENTRYPOINT}")
    }

    @PostMapping("/${ENTRYPOINT}/post")
    fun accept(): ResponseEntity<String> {
        return getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "Continue").build()
    }
}
