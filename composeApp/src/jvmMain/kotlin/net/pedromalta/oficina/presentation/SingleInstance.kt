package net.pedromalta.oficina.presentation

import kotlinx.coroutines.*
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

object SingleInstance {

    private const val PORT = 2678
    private var server: ServerSocket? = null
    private var job: Job? = null

    fun startOrNotify(
        scope: CoroutineScope,
        onSecondInstance: () -> Unit
    ): Boolean {
        return try {
            server = ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"))

            job = scope.launch {
                while (isActive) {
                    try {
                        val socket = server?.accept() ?: break
                        socket.use {
                            withContext(Dispatchers.Main) {
                                onSecondInstance()
                            }
                        }
                    } catch (e: Exception) {
                        if (isActive) e.printStackTrace()
                    }
                }
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            notifyFirstInstance()
            false
        }
    }

    fun stop() {
        runCatching {
            job?.cancel()
            server?.close()
        }
    }

    private fun notifyFirstInstance() {
        runCatching {
            Socket("127.0.0.1", PORT).use {}
        }
    }
}