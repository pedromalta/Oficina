package net.pedromalta.oficina

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import oficina.composeapp.generated.resources.Res
import oficina.composeapp.generated.resources.logo_color
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(1024.dp, 768.dp)
    )
    Window(
        state = state,
        onCloseRequest = ::exitApplication,
        title = "Faturas Oficina",
        icon = painterResource(Res.drawable.logo_color)
    ) {
        window.minimumSize = Dimension(800, 600)
        App()
    }
}