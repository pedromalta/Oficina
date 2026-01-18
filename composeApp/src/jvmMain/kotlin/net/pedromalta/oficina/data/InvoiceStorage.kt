package net.pedromalta.oficina.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import net.pedromalta.oficina.domain.InvoiceItem
import net.pedromalta.oficina.presentation.AppRuntime
import java.io.File

class InvoiceStorage(
    private val file: File = File(AppRuntime.appDataDir(), "invoice_items.json")
) {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    suspend fun save(items: List<InvoiceItem>) = withContext(Dispatchers.IO) {
        runCatching {
            file.writeText(
                json.encodeToString(items)
            )
        }.onFailure {
            it.printStackTrace()
        }
    }

    suspend fun load(): List<InvoiceItem> = withContext(Dispatchers.IO) {
        runCatching {
            if (!file.exists()) return@withContext emptyList()
            json.decodeFromString<List<InvoiceItem>>(file.readText())
        }.getOrElse {
            it.printStackTrace()
            emptyList()
        }
    }
}