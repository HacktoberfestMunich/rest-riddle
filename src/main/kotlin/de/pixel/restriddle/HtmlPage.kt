import org.springframework.http.ResponseEntity

class HtmlPage {

    private var prefix = ""
    private val suffix = "</body>\n</html>"

    private val elements = mutableListOf<String>()

    constructor(title: String = "") {
        prefix = "<!DOCTYPE html>\n"
        prefix += "<html lang=\"en\">\n"
        prefix += "<head>\n"
        prefix += "<style>\n"
        prefix += "body {\n"
        prefix += "  background-image:url(foggy_forest.jpg);\n"
        prefix += "  background-repeat:no-repeat;\n"
        prefix += "  background-size:cover;\n"
        prefix += "}\n"
        prefix += "</style>\n"
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
    fun addLink(path: String, description: String, newPage: Boolean = false): HtmlPage =
        addElement(
            "<a ${
                if (newPage) "target='_blank'"
                else ""
            } href=\"${if (path.startsWith("http")) "" else "/"}$path\">$description</a>"
        )

    fun addHeadline(content: String, level: Int = 1): HtmlPage = addElement("<h$level>$content</h$level>")
    fun addScript(content: String): HtmlPage = addElement("<script type=\"application/javascript\">\n$content\n</script>")

    override fun toString(): String = prefix + elements.joinToString(separator = "\n") + suffix

    fun build(): ResponseEntity<String> = ResponseEntity.ok(toString())
}
