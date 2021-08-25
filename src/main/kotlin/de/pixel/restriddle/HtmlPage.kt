import org.springframework.http.ResponseEntity

class HtmlPage {

    private var prefix = ""
    private val suffix = "</body>\n</html>"

    private val elements = mutableListOf<String>()

    constructor(title: String = "") {
        prefix = "<!DOCTYPE html>\n"
        prefix += "<html lang=\"en\">\n"
        prefix += "<head>\n"
        prefix += "    <meta charset=\"UTF-8\">\n"
        if (title != "")
            prefix += "    <title>$title</title>\n"
        prefix += "</head>\n"
        prefix += "<body>\n"
    }
    
    fun addElement(element: String): HtmlPage {
        elements.add(element)
        return this
    }


    fun addImage(path: String): HtmlPage = addElement("<img src=\"$path\">")
    fun addLink(path: String, description: String): HtmlPage = addElement("<a href=\"$path\">$description</a>")
    fun addHeadline(content: String, level: Int = 1): HtmlPage = addElement("<h$level>$content</h$level>")

    override fun toString(): String = prefix + elements.joinToString(separator = "\n") + suffix

    fun build() = ResponseEntity.ok(toString())
}