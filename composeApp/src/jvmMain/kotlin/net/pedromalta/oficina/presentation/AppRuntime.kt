package net.pedromalta.oficina.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

object AppRuntime {

    val AppScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    fun appDataDir(): File {
        val os = System.getProperty("os.name").lowercase()

        return when {
            os.contains("win") ->
                File(System.getenv("APPDATA"), "Oficina")
            os.contains("mac") ->
                File(System.getProperty("user.home"), "Library/Application Support/Oficina")
            else ->
                File(System.getProperty("user.home"), ".oficina")
        }.apply { mkdirs() }
    }

}