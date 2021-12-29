@file:Suppress("FunctionName")

package io.monosketch.web

import kotlinx.browser.document
import kotlinx.dom.addClass
import org.w3c.dom.Element
import org.w3c.dom.HTMLCollection
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.get

fun Element.Div(classes: String, block: Element.() -> Unit): Element {
    val node = document.createElement("div")
    append(node)
    node.addClass(classes)
    node.block()
    return node
}

val Number.px: String
    get() = "${this}px"

fun styleOf(vararg attributes: Pair<String, String>): String =
    attributes.asSequence().map { "${it.first}: ${it.second}" }.joinToString(";")

fun Element.setAttributes(vararg attrs: Pair<String, Any>) {
    for ((key, value) in attrs) {
        setAttribute(key, value.toString())
    }
}

fun Element.style(vararg attrs: Pair<String, String>) = setAttributes("style" to styleOf(*attrs))

fun HTMLCollection.forEach(block: (HTMLElement) -> Unit) {
    for (i in 0 until length) {
        val element = get(i)
        if (element is HTMLElement) {
            block(element)
        }
    }
}

fun Element.onClick(block: (Event) -> Unit) {
    addEventListener("click", { block(it) })
}
