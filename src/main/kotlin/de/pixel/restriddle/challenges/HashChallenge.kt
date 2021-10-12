package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("hash")
class HashChallenge(
    @Qualifier("movingLink") nextChallenge: ChallengeController,
    @Qualifier("timeslice") private val hashDecryptChallenge: ChallengeController
) : ChallengeController(
    ENTRYPOINT, "A hasshing ssssnake",
    nextChallenge
) {

    companion object {
        const val ENTRYPOINT = "OQde6cbho6Kj9vcuYHYg"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(
        @RequestParam(required = false, defaultValue = "") security: String,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        if (security.isBlank()) {
            response.sendRedirect("/${ENTRYPOINT}?security=sha512")
            return ResponseEntity.ok().build()
        } else {
            val page = getPage().addElement("The snake tries to confuse you... Is there a way out?<br>").addImage("/snake.png")
            return when (security) {
                in listOf("none", "off") -> page.addElement("<a href=\"${nextChallenge?.entrypoint.orEmpty()}\">Thissss way!</a>")
                    .build()
                "sha1" -> page.addElement("dc9adf6ea0024b98499b7d5b4ce8300bd838c193").build()
                "sha256" -> page.addElement("cd1ba87872f23559e47534e798bd53391861902ab9c957e6d916908165439feb").build()
                "sha512" -> page.addElement("571bcf55080d9c416074220a38f17336144bb2136a04c84cd281f6daf9b76372486a05f09efe5237a9240bfae3304d89f7934e42cebffab06b277ed7765d15e6")
                    .build()
                "md5" -> page.addElement("7744c84d84d08ac0d3ed675f355e199f").build()
                "base64" -> page.addElement("PGEgaHJlZj0iekJtOTI2TDMzN3FzN3VVeWhSVjAiPmRlY3J5cHRlZCE8L2E+").build()
                "crc16" -> page.addElement("0450").build()
                "crc32" -> page.addElement("85f4a83c").build()
                else -> page.addElement("I do not know thissss!").build()
            }
        }
    }

    // This hash is encrypted in the hashes above
    @GetMapping("/zBm926L337qs7uUyhRV0")
    fun hashChallengeRedirect(response: HttpServletResponse) {
        response.sendRedirect("/${hashDecryptChallenge.entrypoint}")
    }
}
