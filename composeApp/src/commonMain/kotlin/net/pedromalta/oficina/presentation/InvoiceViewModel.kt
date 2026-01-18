package net.pedromalta.oficina.presentation

import androidx.compose.runtime.mutableStateListOf
import net.pedromalta.oficina.domain.Invoice
import net.pedromalta.oficina.domain.InvoiceItem

class InvoiceViewModel {

    private val _items = mutableStateListOf<InvoiceItem>()
    val items: List<InvoiceItem> get() = _items

    val invoice: Invoice
        get() = Invoice(items = _items)

    fun addItem(description: String, price: Double) {
        _items += InvoiceItem(description, price)
    }

    fun removeItem(item: InvoiceItem) {
        _items.remove(item)
    }
}