package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@Qualifier("dollar")
class CatchTheDollarChallenge(nextChallenge: SwordEnding) : ChallengeController(ENTRYPOINT, "A small goblin is running from you.", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "rSZUgsK56oRY3oYk5OxD"
        private const val X_SIZE = 140
        private const val Y_SIZE = 40
    }

    private var xPos = 20
    private var yPos = 11
    private var xPosTarget = 120
    private var yPosTarget = 30

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(@RequestParam(defaultValue = "") direction: String): ResponseEntity<String> {
        val page = getPage()

        if (direction.isBlank()) {
            return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(URI.create("/$ENTRYPOINT?direction=up")).build()
        }

        // Move
        when (direction) {
            "up" -> {
                yPos--
                yPosTarget++
            }
            "down" -> {
                yPos++
                yPosTarget--
            }
            "right" -> {
                xPos++
                xPosTarget--
            }
            "left" -> {
                xPos--
                xPosTarget++
            }
        }

        // Validate Positions
        if (xPos < 0)
            xPos = 0
        if (xPos > X_SIZE)
            xPos = X_SIZE
        if (yPos < 0)
            yPos = 0
        if (yPos > Y_SIZE)
            yPos = Y_SIZE
        if (xPosTarget < 0)
            xPosTarget = 0
        if (xPosTarget > X_SIZE)
            xPosTarget = X_SIZE
        if (yPosTarget < 0)
            yPosTarget = 0
        if (yPosTarget > Y_SIZE)
            yPosTarget = Y_SIZE

        if (xPos == xPosTarget && yPos == yPosTarget)
            return page.addElement("<a href=\"${nextChallenge?.entrypoint.orEmpty()}\">Ok ok ok! This way!</a>").build()

        page.addElement("He might know the way...")

        for (i in 0..Y_SIZE) {
            var element = ""
            for (j in 0..X_SIZE) {
                if (i == yPos && j == xPos)
                    element += '@'
                else if (i == yPosTarget && j == xPosTarget)
                    element += '$'
                else
                    element += '#'
            }
            page.addElement("$element<br>")
        }
        return page.build()
    }
}
