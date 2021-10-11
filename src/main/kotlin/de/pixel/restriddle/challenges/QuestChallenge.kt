package de.pixel.restriddle.challenges

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@Qualifier("quest")
class QuestChallenge(
    @Qualifier("anyOptions") nextChallenge: ChallengeController, private val end: ChallengeEnd
) : ChallengeController(ENTRYPOINT, "A quest to go?", nextChallenge) {

    companion object {
        const val ENTRYPOINT = "plSDzW8EyPJcg33nFkUs"
        const val SWORD_NAME = "Imperial Living Two-handed Sword of Eternal Charisma"
        const val ARMOR_NAME = "Evil Fiery Chain Mail of Doomed Generosity"
        const val POTIONS_NAME = "Infamous Undead's Draughts of Hellish Light Rain"
        const val BELT_NAME = "Lawful Destroyers' Belt of Ghostly Force"
    }

    @GetMapping("/${ENTRYPOINT}")
    fun challenge(): ResponseEntity<String> {
        return getPage()
            .addElement(
                """
                <p>
                A huge dragon appears on the horizon. To protect your land and solve all given puzzles of the king, you need
                to collect the 4 blessed items. You can find them at the end of the following challenges. 
                </p>
                <h3>Find them and defeat the dragon</h3>
                """.trimIndent()
            )
            .addLink(nextChallenge?.entrypoint.orEmpty(), "Go to your adventure.")
            .addElement(
                """
                <h3>After your adventure return here and name the 4 blessed items:</h3>
                <p>
                    <form action="$ENTRYPOINT/post" method="post">
                        <input type="text" id="item" name="input">
                        <input type="text" id="item" name="input">
                        <input type="text" id="item" name="input">
                        <input type="text" id="item" name="input">
                      <input type="submit" value="Check the names">
                    </form>
                </p>
            """.trimIndent()
            )
            .addImage("beast-ga999eb01e.png")
            .build()
    }


    @PostMapping("/${ENTRYPOINT}/post")
    fun itemNames(@RequestParam("item") items: List<String>, response: HttpServletResponse) {
        if (items.size == 4
            && items.contains(SWORD_NAME)
            && items.contains(ARMOR_NAME)
            && items.contains(POTIONS_NAME)
            && items.contains(BELT_NAME)
        ) {
            response.sendRedirect(end.entrypoint)
        } else {
            response.sendRedirect("/${ENTRYPOINT}")
        }
    }
}
