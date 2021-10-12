package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.concurrent.timer
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RestController
@Qualifier("simultanPress")
class SimultaneouslyPressChallenge(@Qualifier("dollar") nextChallenge: ChallengeController) :
    ChallengeController(ENTRYPOINT, "Security button", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "0k6oKGGPTsSJr3XhTjVc"
        private const val PEOPLE_COUNT = 5
    }

    private val resetTimer =
        timer("SimultaneouslyPressResetTimer", true, 0, Duration.seconds(2).inWholeMilliseconds) {
            reset()
        }

    private var ips = mutableSetOf<String>()

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        val page = getPage()
        return page
            .addElement("<p>A huge rock blocks your way</p>")
            .addElement("You will need at least five people to move it.")
            .addElement(
                """
                <form action="$ENTRYPOINT/post" method="post">
                  <input type="submit" value="PUSH">
                </form>
            """.trimIndent()
            ).build()

    }

    @PostMapping("/${ENTRYPOINT}/post")
    fun accept(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<String> {
        ips.add(request.remoteHost)

        return if (ips.size >= PEOPLE_COUNT) {
            getPage().addLink("${nextChallenge?.entrypoint.orEmpty()}", "Security disabled. Proceed").build()
        } else {
            response.sendRedirect("/$ENTRYPOINT")
            ResponseEntity.ok().build()
        }
    }

    private fun reset() {
        ips = mutableSetOf()
    }
}
