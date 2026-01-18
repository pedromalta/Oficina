package net.pedromalta.oficina.presentation

import net.pedromalta.oficina.domain.Invoice
import net.pedromalta.oficina.domain.Shop

expect fun printInvoice(invoice: Invoice, shop: Shop)