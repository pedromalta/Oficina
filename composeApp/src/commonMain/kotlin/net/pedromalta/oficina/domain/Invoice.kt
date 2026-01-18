package net.pedromalta.oficina.domain

data class Invoice(
    val items: List<InvoiceItem>
) {
    val total: Double = items.sumOf { it.price }

    val totalParts: Double = items.sumOf {
        if (it.type == InvoiceItemType.PART){
            it.price
        } else {
            0.0
        }
    }

    val totalServices: Double = items.sumOf {
        if (it.type == InvoiceItemType.SERVICE){
            it.price
        } else {
            0.0
        }
    }

    val totalThirdPartyServices: Double = items.sumOf {
        if (it.type == InvoiceItemType.THIRD_PARTY_SERVICE){
            it.price
        } else {
            0.0
        }
    }
}
