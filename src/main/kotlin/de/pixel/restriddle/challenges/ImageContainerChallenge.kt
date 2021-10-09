package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("imageContainer")
class ImageContainerChallenge(@Qualifier("keypad") nextChallenge: ChallengeController) : ChallengeController(
    ENTRYPOINT, "Cat in a Image",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "lHjKMVVICUFo355UVqWk"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage().addImage("uDG7KGAIF4QQLQTdxJQS.png").build()
    }

    // NEXT ID in image is 'O5ZdvcloSZ6J42zZh3vy'
    @GetMapping("/O5ZdvcloSZ6J42zZh3vy")
    fun imageHash(response: HttpServletResponse) {
        response.sendRedirect(nextChallenge?.entrypoint.orEmpty())
    }
}
