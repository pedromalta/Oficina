package net.pedromalta.oficina.presentation

import net.pedromalta.oficina.domain.Invoice

expect fun printInvoice(invoice: Invoice, shopName: String)