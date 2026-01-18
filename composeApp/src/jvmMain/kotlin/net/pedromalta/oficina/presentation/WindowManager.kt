package net.pedromalta.oficina.presentation

import java.awt.Window

object WindowManager {
    lateinit var window: Window

    fun bringToFront() {
        window.apply {
            toFront()
            isVisible = true
            requestFocus()
        }
    }
}
