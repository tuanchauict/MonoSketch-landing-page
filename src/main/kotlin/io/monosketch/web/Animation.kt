package io.monosketch.web

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.addClass
import kotlinx.dom.hasClass
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLElement
import org.w3c.dom.Window

/**
 * A class which manages animated images in the page
 */
class Animation {
    private val images: List<AnimatedImage> = document.getElementsByClassName("animation-container")
        .asSequence()
        .filterIsInstance<HTMLElement>()
        .map(::AnimatedImage)
        .toList()

    private val autoPlayImages: Sequence<AnimatedImage> = images.filter { it.isAutoPlay }.asSequence()

    init {
        onWindowChange()
    }

    fun onWindowChange() {
        val animationRange = window.getAnimationRange()

        autoPlayImages.forEach {
            val isPlay = it.topPx in animationRange || it.bottomPx in animationRange
            if (isPlay) {
                it.play()
            } else {
                it.stop()
            }
        }
    }

    private fun Window.getAnimationRange(): IntRange {
        val windowTopPx = scrollY.toInt()
        val windowBottomPx = windowTopPx + innerHeight
        return (windowTopPx + INANIMATABLE_GAP)..(windowBottomPx - INANIMATABLE_GAP)
    }

    companion object {
        private const val INANIMATABLE_GAP = 100
    }
}

private class AnimatedImage(private val container: HTMLElement) {
    private val defaultDurationMillis: Int = container.getDurationMillis() ?: DEFAULT_DURATION_MILLIS
    private val frames: List<Frame> = container.getElementsByClassName("frame")
        .asSequence()
        .filterIsInstance<HTMLElement>()
        .map(::Frame)
        .toList()

    val isAutoPlay: Boolean = container.hasAttribute(ATTR_AUTO_PLAY)

    val topPx: Int
        get() = container.offsetTop

    val bottomPx: Int
        get() = container.offsetTop + container.clientHeight

    private var frameTimeout: Cancelable? = null

    private var currentFrameIndex: Int = 0

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

    fun play() {
        if (container.hasClass(CLASS_PLAY)) {
            return
        }
        container.addClass(CLASS_PLAY)
        showFrame(currentFrameIndex)
    }

    private fun showFrame(frameIndex: Int) {
        currentFrameIndex = frameIndex
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

    fun stop() {
        currentFrameIndex = 0
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
