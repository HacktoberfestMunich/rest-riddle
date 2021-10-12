package de.pixel.restriddle.challenges

import HtmlPage

abstract class ChallengeController(
    val entrypoint: String,
    private val title: String,
    internal val nextChallenge: ChallengeController?
) {

    var calls = 0

    open fun getPage(): HtmlPage {
        calls++
        return HtmlPage(title).addHeadline(title)
    }
}
