package io.monosketch.web

import kotlinx.browser.document
import kotlinx.dom.appendText
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

/**
 * A class for handling show/hide side note
 */
class SideNote(
    private val node: HTMLElement
) {
    private val noteContent: String = node.getAttribute(ATTR_DATA_CONTENT).orEmpty().format()
    private var modal: Element? = null

    init {
        node.onClick {
            if (modal != null) {
                dismiss()
            } else {
                show()
            }
        }
    }

    private fun show() {
        modal = document.body?.Div("side-note-modal") {
            style(
                "left" to (node.offsetLeft + node.offsetWidth + 8).px,
                "top" to (node.offsetTop).px,
            )
            Div("") {
                innerHTML = noteContent
            }
            Div("close") {
                appendText("×")
                onClick {
                    println("Con heo")
                    dismiss()
                }
            }
        }
    }

    private fun String.format(): String {
        @Suppress("RegExpRedundantEscape")
        val regex = "\\[(.+?)\\]\\((.+?)\\)".toRegex(RegexOption.MULTILINE)
        return regex.replace(this) {
            val (_, text, href) = it.groupValues
            val formattedHref = if (href.startsWith("https://")) href else "$GITHUB/$href"
            """<a href="$formattedHref">$text</a>"""
        }
    }

    private fun dismiss() {
        modal?.remove()
        modal = null
    }

    companion object {
        private const val ATTR_DATA_CONTENT = "data-content"
        private const val GITHUB = "https://github.com/tuanchauict/MonoSketch"

        fun register() {
            document.getElementsByClassName("side-note").forEach(::SideNote)
        }
    }
}
