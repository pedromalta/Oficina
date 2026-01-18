package net.pedromalta.oficina.domain

data class Invoice(
    val items: List<InvoiceItem>
) {
    val parts: List<InvoiceItem> = items.filter { it.type == InvoiceItemType.PART }
    val services: List<InvoiceItem> = items.filter { it.type == InvoiceItemType.SERVICE }
    val thirdPartyServices: List<InvoiceItem> =
        items.filter { it.type == InvoiceItemType.THIRD_PARTY_SERVICE }

    val total = MoneyAmount(items.sumOf { it.price.amountInCents })

    val totalParts = MoneyAmount(parts.sumOf { it.price.amountInCents })

    val totalServices = MoneyAmount(services.sumOf { it.price.amountInCents })

    val totalThirdPartyServices = MoneyAmount(thirdPartyServices.sumOf {
        it.price.amountInCents
    })
}
