package net.pedromalta.oficina.domain

data class InvoiceItem(
    val description: String,
    val price: MoneyAmount,
    val type: InvoiceItemType,
)

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

enum class InvoiceItemType {
    PART,
    SERVICE,
    THIRD_PARTY_SERVICE,
}