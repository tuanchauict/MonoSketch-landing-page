package io.monosketch.web

import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLElement

/**
 * A class which manages animated images in the page
 */
class Animation {
    private val images: List<AnimatedImage> = document.getElementsByClassName("animation-container")
        .asSequence()
        .filterIsInstance<HTMLElement>()
        .map(::AnimatedImage)
        .toList()

    fun onWindowChange() {
        // TODO: Update animation for images
    }
}

private class AnimatedImage(private val container: HTMLElement) {
    private val defaultDurationMillis: Int = container.getDurationMillis() ?: DEFAULT_DURATION_MILLIS
    private val frames: List<Frame> = container.getElementsByClassName("frame")
        .asSequence()
        .filterIsInstance<HTMLElement>()
        .map(::Frame)
        .toList()

    private val isAutoPlay: Boolean = container.hasAttribute(ATTR_AUTO_PLAY)

    private var frameTimeout: Cancelable? = null

    init {
        if (!isAutoPlay) {
            container.addEventListener("mouseenter", {
                play()
            })
            container.addEventListener("mouseleave", {
                stop()
            })
        }
    }

    private fun play() {
        container.addClass(CLASS_PLAY)
        showFrame(0)
    }

    private fun showFrame(frameIndex: Int) {
        frameTimeout?.cancel()

        frames[frameIndex.previousFrameIndex].setVisibility(false)

        val currentFrame = frames[frameIndex]
        currentFrame.setVisibility(true)

        val duration = currentFrame.durationMillis ?: defaultDurationMillis
        frameTimeout = setTimeout(duration) {
            showFrame(frameIndex.nextFrameIndex)
        }
    }

    private val Int.previousFrameIndex: Int
        get() = if (this > 0) this - 1 else frames.lastIndex

    private val Int.nextFrameIndex: Int
        get() = if (this < frames.lastIndex) this + 1 else 0

    private fun stop() {
        container.getElementsByClassName(CLASS_CURRENT)
            .asSequence()
            .forEach { it.removeClass(CLASS_CURRENT) }
        frameTimeout?.cancel()
        frameTimeout = null

        container.removeClass(CLASS_PLAY)
    }

    private class Frame(private val frameElement: HTMLElement) {
        val durationMillis: Int? = frameElement.getDurationMillis()

        fun setVisibility(isVisible: Boolean) {
            if (isVisible) {
                frameElement.addClass(CLASS_CURRENT)
            } else {
                frameElement.removeClass(CLASS_CURRENT)
            }
        }
    }

    companion object {
        private const val ATTR_DURATION = "data-duration"
        private const val ATTR_AUTO_PLAY = "data-auto-play"

        private const val DEFAULT_DURATION_MILLIS = 400

        private const val CLASS_PLAY = "play"
        private const val CLASS_CURRENT = "current"

        private fun HTMLElement.getDurationMillis(): Int? = getAttribute(ATTR_DURATION)?.toIntOrNull()
    }
}
