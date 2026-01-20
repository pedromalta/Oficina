package net.pedromalta.oficina

import net.pedromalta.oficina.presentation.SingleInstance
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.pedromalta.oficina.data.InvoiceStorage
import net.pedromalta.oficina.presentation.AppRuntime
import net.pedromalta.oficina.presentation.InvoiceViewModel
import net.pedromalta.oficina.presentation.WindowManager
import oficina.composeapp.generated.resources.Res
import oficina.composeapp.generated.resources.logo_color
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension

fun main() {
    val storage = InvoiceStorage()
    val invoiceViewModel = InvoiceViewModel().apply {
        AppRuntime.AppScope.launch {
            setItems(storage.load())
        }
    }

    val isFirst = SingleInstance.startOrNotify(
        scope = AppRuntime.AppScope
    ) {
        WindowManager.bringToFront()
    }

    if (!isFirst) return

    application {
        val state = rememberWindowState(
            size = DpSize(1024.dp, 768.dp)
        )

        Window(
            state = state,
            onCloseRequest = {
                AppRuntime.AppScope.launch {
                    SingleInstance.stop()
                    storage.save(invoiceViewModel.invoice.items)
                    AppRuntime.AppScope.cancel()
                    exitApplication()
                }
            },
            title = "Faturas Oficina",
            icon = painterResource(Res.drawable.logo_color)
        ) {
            WindowManager.window = window
            window.minimumSize = Dimension(800, 600)
            App(invoiceViewModel)
        }
    }

}