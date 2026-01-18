package net.pedromalta.oficina.presentation

import androidx.compose.runtime.mutableStateListOf
import net.pedromalta.oficina.domain.Invoice
import net.pedromalta.oficina.domain.InvoiceItem
import net.pedromalta.oficina.domain.InvoiceItemType

class InvoiceViewModel {

    private val _items = mutableStateListOf<InvoiceItem>()
    val items: List<InvoiceItem> get() = _items

    val parts: List<InvoiceItem> get() = _items.filter { it.type == InvoiceItemType.PART }

    val services: List<InvoiceItem> get() = _items.filter { it.type == InvoiceItemType.SERVICE }

    val thirdPartService: List<InvoiceItem> get() = _items.filter { it.type == InvoiceItemType.THIRD_PARTY_SERVICE }

    val invoice: Invoice
        get() = Invoice(items = _items)

    fun addItem(description: String, price: Double, type: InvoiceItemType) {
        _items += InvoiceItem(description, price, type)
    }

    fun removeItem(item: InvoiceItem) {
        _items.remove(item)
    }
}