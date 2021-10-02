package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Qualifier("caesar")
class CaesarChallenge(@Qualifier("end") nextChallenge: ChallengeController) : ChallengeController(ENTRYPOINT, Caesar.decrypt("A secure encryption?"), nextChallenge) {

    companion object {
        const val ENTRYPOINT = "HUtTz3r5gDwWFjgicaRA"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addElement(Caesar.encrypt("This might be a little tricky."))
            .addLink(Caesar.encrypt(nextChallenge?.entrypoint.orEmpty()), Caesar.decrypt("Next page")).build()
    }
}

object Caesar {

    private const val key = 3;

    fun encrypt(s: String, key: Int = this.key): String {
        val offset = key % 26
        if (offset == 0) return s
        var d: Char
        val chars = CharArray(s.length)
        for ((index, c) in s.withIndex()) {
            if (c in 'A'..'Z') {
                d = c + offset
                if (d > 'Z') d -= 26
            } else if (c in 'a'..'z') {
                d = c + offset
                if (d > 'z') d -= 26
            } else
                d = c
            chars[index] = d
        }
        return chars.joinToString("")
    }

    fun decrypt(s: String, key: Int = this.key): String {
        return encrypt(s, 26 - key)
    }
}

