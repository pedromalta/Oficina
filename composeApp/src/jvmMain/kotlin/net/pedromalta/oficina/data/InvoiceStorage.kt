package net.pedromalta.oficina.data

import kotlinx.serialization.json.Json
import net.pedromalta.oficina.domain.InvoiceItem
import java.io.File

class InvoiceStorage(
    private val file: File = File("invoice_items.json")
) {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun save(items: List<InvoiceItem>) {
        file.writeText(json.encodeToString(items))
    }

    fun load(): List<InvoiceItem> {
        if (!file.exists()) return emptyList()
        return json.decodeFromString(file.readText())
    }
}