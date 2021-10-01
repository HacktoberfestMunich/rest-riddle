package de.pixel.restriddle.challenges

import HtmlPage

abstract class ChallengeController(
    val entrypoint: String,
    private val title: String,
    internal val nextChallenge: ChallengeController?
) {

    fun getPage(): HtmlPage = HtmlPage(title).addHeadline(title)
}
