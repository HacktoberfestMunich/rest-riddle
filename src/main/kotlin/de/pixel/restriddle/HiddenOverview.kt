package de.pixel.restriddle

import HtmlPage
import de.pixel.restriddle.challenges.ChallengeController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HiddenOverview(private val challenges: List<ChallengeController>) {

    @GetMapping("/b60NgE2muTfBBWuH90ztq1sbFYx46shpcGd7Ltx2")
    fun hiddenOverview(): ResponseEntity<String> {
        val page = HtmlPage().addHeadline("Overview")

        page.addElement("<div style=\"float:left\">")
        challenges.sortedByDescending(ChallengeController::calls).forEach {
            page.addElement("<p>")
                .addLink(it.entrypoint, it::class.simpleName!!, true)
                .addElement(" ➡️ ${it.calls}</p>")
        }
        page.addElement("</div>")

        page.addElement("<div style=\"float:right\">")
        page.addImage("b60NgE2muTfBBWuH90ztq1sbFYx46shpcGd7Ltx21.png", 900)
        page.addElement("</div>")
        return page.build()
    }
}
