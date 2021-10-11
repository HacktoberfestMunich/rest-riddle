package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("disabledButton")
class DisabledButtonChallenge(@Qualifier("quest") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "Continue",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "XbvUOiA8iNmHrtIcnDZf"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addElement(
                """
                <form action="$ENTRYPOINT/post" method="post">
                  <input type="submit" value="Submit" disabled>
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
