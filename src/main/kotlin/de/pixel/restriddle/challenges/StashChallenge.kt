package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import kotlin.random.Random

@RestController
@Qualifier("stash")
class StashChallenge(
    @Qualifier("hash") private val upperChallenge: ChallengeController,
    @Qualifier("fanout") private val lowerChallenge: ChallengeController
) :
    ChallengeController(
        ENTRYPOINT, "Stashing things",
        null
    ) {

    companion object {
        const val ENTRYPOINT = "gXeZjry9RoPsbZTCNvV3"
        private const val MAX_STACK_SIZE = 30
        private const val STACK_OFFSET = 5
    }

    private var position = STACK_OFFSET + Random.nextInt(MAX_STACK_SIZE - STACK_OFFSET)

    @GetMapping("/${ENTRYPOINT}/action")
    fun action(): ResponseEntity<String> {
        return getPage()
            .addHeadline("This is not the method you are looking for.")
            .build()
    }

    @PutMapping("/${ENTRYPOINT}/action")
    fun actionPush(response: HttpServletResponse) {
        if (position < MAX_STACK_SIZE)
            position++
        response.sendRedirect("/${ENTRYPOINT}")
    }

    @DeleteMapping("/${ENTRYPOINT}/action")
    fun actionPop(response: HttpServletResponse) {
        if (position > 0)
            position--
        response.sendRedirect("/${ENTRYPOINT}")
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return when {
            position in 0 until MAX_STACK_SIZE + 1 -> getPage()
                .addElement("All or nothing he yelled at us. Stash all the boxes or get them out of the way.<br>")
                .addLink("${ENTRYPOINT}/action", "", true)
                .addElement(drawStack())
                .build()
            position < 0 -> getPage()
                .addLink(upperChallenge.entrypoint, "Stash emptied")
                .build()
            else -> getPage()
                .addLink(lowerChallenge.entrypoint, "Stash Overflow")
                .build()
        }
    }

    private fun drawStack(): String {
        val slots = mutableListOf<String>()
        for (index in 0..MAX_STACK_SIZE) {
            if (index <= position) {
                slots.add("\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6\uD83D\uDCE6")
            } else {
                slots.add("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜")
            }
        }
        return slots.joinToString("<br/>\n")
    }
}
