import io.monosketch.web.SideNote
import kotlinx.browser.window

fun main() {
    window.onload = {
        onReady()
    }
}

private fun onReady() {
    SideNote.register()
}
