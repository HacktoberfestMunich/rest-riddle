package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("hackerman")
class HackermanChallenge(@Qualifier("deleteme") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "Time hacking time", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "mPSfA4qxXOKNg47rJtnK"
        private const val HACKERMAN_YOUTUBE = "https://www.youtube.com/watch?v=KEkrWRHCDQU"
        private const val QUESTION = "What do we need to proceed to make a direct link to the kernel?"
        private const val ANSWER = "segmentation violation"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(servletRequest: HttpServletRequest): ResponseEntity<String> {
        return getPage()
            .addLink(HACKERMAN_YOUTUBE, "HACKERMAN'S HACKING TUTORIALS", true)
            .addElement(
                """
                <br>
                <form action="${ENTRYPOINT}/post" method="post">
                  <label for="input">$QUESTION</label><br>
                  <input type="text" id="input" name="input">
                  <input type="submit" value="Submit">
                </form>
            """.trimIndent()
            )
            .build()
    }

    @PostMapping("/${ENTRYPOINT}/post")
    fun accept(@RequestParam(defaultValue = "") input: String, response: HttpServletResponse): ResponseEntity<String> {
        return if (input.isNotBlank() && input.contains(ANSWER, true)) {
            getPage().addLink(nextChallenge?.entrypoint.orEmpty(), "Continue").build()
        } else {
            response.sendRedirect("/${ENTRYPOINT}")
            ResponseEntity.ok().build()
        }
    }
}
