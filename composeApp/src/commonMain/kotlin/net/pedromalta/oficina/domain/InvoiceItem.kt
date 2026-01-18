package net.pedromalta.oficina.domain

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceItem(
    val description: String,
    val price: MoneyAmount,
    val type: InvoiceItemType,
)

@Serializable
data class MoneyAmount(
    val amountInCents: Long = 0
) {
    override fun toString(): String {
        val reais = amountInCents / 100
        val cents = amountInCents % 100
        val centsString = cents.toString().padStart(2, '0')
        return "R$ $reais,$centsString"
    }
}

@Serializable
enum class InvoiceItemType {
    PART,
    SERVICE,
    THIRD_PARTY_SERVICE,
}