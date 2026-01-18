package net.pedromalta.oficina.domain

data class InvoiceItem(
    val description: String,
    val price: Double,
    val type: InvoiceItemType,
)

enum class InvoiceItemType {
    SERVICE,
    THIRD_PARTY_SERVICE,
    PART
}