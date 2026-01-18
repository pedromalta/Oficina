package net.pedromalta.oficina.presentation

import net.pedromalta.oficina.domain.Invoice

import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterJob
import java.awt.Graphics

actual fun printInvoice(invoice: Invoice, shopName: String) {
    val printerJob = PrinterJob.getPrinterJob()
    printerJob.jobName = "Fatura"

    printerJob.setPrintable { graphics: Graphics, pageFormat: PageFormat, pageIndex: Int ->
        if (pageIndex > 0) return@setPrintable Printable.NO_SUCH_PAGE

        val g2d = graphics as java.awt.Graphics2D
        g2d.translate(pageFormat.imageableX, pageFormat.imageableY)

        var y = 20
        g2d.drawString(shopName, 100, y)
        y += 20

        invoice.items.forEach {
            g2d.drawString(it.description, 20, y)
            g2d.drawString("R$ %.2f".format(it.price), 300, y)
            y += 15
        }

        y += 10
        g2d.drawString("TOTAL: R$ %.2f".format(invoice.total), 20, y)

        Printable.PAGE_EXISTS
    }

    if (printerJob.printDialog()) {
        printerJob.print()
    }
}