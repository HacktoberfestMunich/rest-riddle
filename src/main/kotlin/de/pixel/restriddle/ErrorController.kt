package de.pixel.restriddle

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest


@Controller
@RequestMapping("/error")
class ErrorViewController @Autowired constructor(private val errorAttributes: ErrorAttributes) : ErrorController {

    companion object {
        private const val EXCEPTION_KEY = "exception"
        private val LOGGER = LoggerFactory.getLogger(ErrorViewController::class.java)
    }

    @ResponseBody
    @RequestMapping(produces = [MediaType.TEXT_PLAIN_VALUE])
    fun error(model: Model, request: HttpServletRequest): ResponseEntity<String> {
        val errorMap: Map<String, Any?> = if (model.containsAttribute(EXCEPTION_KEY)) {
            model.asMap()
        } else {
            getErrorAttributes(request)
        }

        val status = HttpStatus.valueOf(getError(errorMap, "status") as Int)
        val path = getError(errorMap, "path") as String

        if (status != HttpStatus.NOT_FOUND) {
            LOGGER.warn("An error occurred: $status - $path")
        }

        return ResponseEntity("$status - $path", status)
    }

    private fun getErrorAttributes(request: HttpServletRequest): Map<String, Any?> {
        val webrequest: WebRequest = ServletWebRequest(request)
        return errorAttributes.getErrorAttributes(webrequest, ErrorAttributeOptions.defaults())
    }

    private fun getError(errorMap: Map<String, Any?>, k: String): Any? {
        return errorMap.getOrDefault(k, "")
    }
}

