package io.monosketch.web

import kotlinx.browser.document
import org.w3c.dom.HTMLElement

object Graph {
    fun register() {
        document.getElementsByClassName("graph lazy")
            .asSequence()
            .filterIsInstance<HTMLElement>()
            .forEach {
                it.inflate()
            }
    }

    private fun HTMLElement.inflate() {
        val key = getAttribute("data-graph") ?: return
        val graph = GraphRes.get(key) ?: return
        innerText = graph
    }
}
