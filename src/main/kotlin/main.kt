import io.monosketch.web.Animation
import io.monosketch.web.SideNote
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    window.onload = {
        onReady()
    }
}

private fun onReady() {
    val body = document.body ?: return
    SideNote.register()
    val animation = Animation()
    body.onscroll = {
        animation.onWindowChange()
    }

    window.onresize = {
        animation.onWindowChange()
    }
}
