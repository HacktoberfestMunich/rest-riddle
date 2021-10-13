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
        ENTRYPOINT, "Guard of the stashes",
        null
    ) {

    companion object {
        const val ENTRYPOINT = "gXeZjry9RoPsbZTCNvV3"
        private const val MAX_STACK_SIZE = 30
        private const val STACK_OFFSET = 5
    }

    private var position = STACK_OFFSET + Random.nextInt(MAX_STACK_SIZE - STACK_OFFSET)


    @PutMapping("/${ENTRYPOINT}")
    fun actionPush(response: HttpServletResponse) {
        if (position < MAX_STACK_SIZE)
            position++
        response.sendRedirect("/${ENTRYPOINT}")
    }

    @DeleteMapping("/${ENTRYPOINT}")
    fun actionPop(response: HttpServletResponse) {
        if (position >= 0)
            position--
        response.sendRedirect("/${ENTRYPOINT}")
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return when {
            position in 0 until MAX_STACK_SIZE -> getPage()
                .addElement("<div style=\"float:left\">")
                .addElement("Hello my Friends! I am <string>Herald</strong> a famous in this country.<br>")
                .addElement("Do you see this big <string>stack</strong> of boxes? We somehow have to get them out our way.<br>But at least... do not stack it even higher.<br>")
                .addImage("herald.png")
                .addElement("</div>")
                .addElement("<div style=\"float:right\">")
                .addElement(drawStack())
                .addElement("</div>")
                .build()
            position < 0 -> getPage()
                .addLink(upperChallenge.entrypoint, "You can continue")
                .build()
            else -> getPage()
                .addLink(lowerChallenge.entrypoint, "The stash of boxes collapsed")
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
