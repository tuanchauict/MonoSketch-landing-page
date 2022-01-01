package io.monosketch.web

import kotlinx.browser.window

fun post(action: () -> Unit): Cancelable = setTimeout(0, action)

fun setTimeout(durationMillis: Int, action: () -> Unit): Cancelable =
    if (durationMillis == 0) {
        AnimationFrame(window.requestAnimationFrame { action() })
    } else {
        Timeout(window.setTimeout(action, durationMillis))
    }

fun setInterval(durationMillis: Int, action: () -> Unit): Cancelable =
    Interval(window.setInterval(action, durationMillis))

interface Cancelable {
    fun cancel()
}

private class Timeout(private val id: Int) : Cancelable {
    override fun cancel() = window.clearTimeout(id)
}

private class Interval(private val id: Int) : Cancelable {
    override fun cancel() = window.clearInterval(id)
}

private class AnimationFrame(private val id: Int) : Cancelable {
    override fun cancel() = window.cancelAnimationFrame(id)
}
